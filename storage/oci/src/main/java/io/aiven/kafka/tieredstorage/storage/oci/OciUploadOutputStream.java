/*
 * Copyright 2021 Aiven Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.aiven.kafka.tieredstorage.storage.oci;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CommitMultipartUploadDetails;
import com.oracle.bmc.objectstorage.model.CommitMultipartUploadPartDetails;
import com.oracle.bmc.objectstorage.model.CreateMultipartUploadDetails;
import com.oracle.bmc.objectstorage.requests.AbortMultipartUploadRequest;
import com.oracle.bmc.objectstorage.requests.CommitMultipartUploadRequest;
import com.oracle.bmc.objectstorage.requests.CreateMultipartUploadRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.requests.UploadPartRequest;
import com.oracle.bmc.objectstorage.responses.CreateMultipartUploadResponse;
import com.oracle.bmc.objectstorage.responses.UploadPartResponse;
import io.aiven.kafka.tieredstorage.storage.ObjectKey;
import io.aiven.kafka.tieredstorage.storage.upload.AbstractUploadOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;


public class OciUploadOutputStream extends AbstractUploadOutputStream<CommitMultipartUploadPartDetails> {

    private static final Logger log = LoggerFactory.getLogger(OciUploadOutputStream.class);

    private final ObjectStorageClient client;
    private final String namespaceName;

    public OciUploadOutputStream(final String namespaceName,
                                 final String bucketName,
                                 final ObjectKey key,
                                 final int partSize,
                                 final ObjectStorageClient client) {
        super(bucketName, key.value(), partSize);
        this.namespaceName = namespaceName;
        this.client = client;
    }

    @Override
    protected String createMultipartUploadRequest(String bucketName, String key) {
        CreateMultipartUploadDetails createDetails = CreateMultipartUploadDetails.builder()
                .object(key)
                .contentType("application/octet-stream")
                .build();

        CreateMultipartUploadRequest initialRequest = CreateMultipartUploadRequest.builder()
                .namespaceName(namespaceName)
                .bucketName(bucketName)
                .createMultipartUploadDetails(createDetails)
                .build();
        final CreateMultipartUploadResponse initiateResult = client.createMultipartUpload(initialRequest);
        String uploadId = initiateResult.getMultipartUpload().getUploadId();
        log.debug("Create new multipart upload request: {}", uploadId);
        return uploadId;
    }

    protected void uploadAsSingleFile(final String bucketName, final String key, final InputStream inputStream, final int size) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .namespaceName(namespaceName)
                .bucketName(bucketName)
                .objectName(key)
                .putObjectBody(inputStream)
                .build();

        client.putObject(putObjectRequest);
    }

    @Override
    protected CommitMultipartUploadPartDetails _uploadPart(String bucketName, String key, String uploadId, int partNumber, final InputStream in, final int actualPartSize) {
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .namespaceName(namespaceName)
                .bucketName(bucketName)
                .objectName(key)
                .uploadId(uploadId)
                .uploadPartNum(partNumber)
                .uploadPartBody(in)
                .build();

        UploadPartResponse uploadResult = client.uploadPart(uploadPartRequest);

        return CommitMultipartUploadPartDetails.builder()
                        .partNum(partNumber)
                        .etag(uploadResult.getETag())
                        .build();
    }

    @Override
    protected void abortUpload(String bucketName, String key, String uploadId) {
        final var request = AbortMultipartUploadRequest.builder()
                .namespaceName(namespaceName)
                .bucketName(bucketName)
                .objectName(key)
                .uploadId(uploadId)
                .build();
        client.abortMultipartUpload(request);
    }

    @Override
    protected void completeUpload(List<CommitMultipartUploadPartDetails> completedParts, String bucketName, String key, String uploadId) {
        CommitMultipartUploadDetails commitDetails = CommitMultipartUploadDetails.builder()
                .partsToCommit(completedParts)
                .build();

        CommitMultipartUploadRequest commitReq = CommitMultipartUploadRequest.builder()
                .namespaceName(namespaceName)
                .bucketName(bucketName)
                .objectName(key)
                .uploadId(uploadId)
                .commitMultipartUploadDetails(commitDetails)
                .build();

        client.commitMultipartUpload(commitReq);
    }

}
