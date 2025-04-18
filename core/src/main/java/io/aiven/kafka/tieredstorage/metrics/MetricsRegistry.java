/*
 * Copyright 2023 Aiven Oy
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

package io.aiven.kafka.tieredstorage.metrics;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.MetricNameTemplate;
import org.apache.kafka.common.TopicPartition;

import io.aiven.kafka.tieredstorage.ObjectKeyFactory;

public class MetricsRegistry {
    public static final String METRIC_CONTEXT = "aiven.kafka.server.tieredstorage";
    static final String METRIC_GROUP = "remote-storage-manager-metrics";
    static final String TAG_NAME_OBJECT_TYPE = "object-type";
    static final String[] OBJECT_TYPE_TAG_NAMES = {TAG_NAME_OBJECT_TYPE};
    static final String TAG_NAME_TOPIC = "topic";
    static final String[] TOPIC_TAG_NAMES = {TAG_NAME_TOPIC};
    static final String[] TOPIC_OBJECT_TYPE_TAG_NAMES = {TAG_NAME_TOPIC, TAG_NAME_OBJECT_TYPE};
    static final String TAG_NAME_PARTITION = "partition";
    static final String[] TOPIC_PARTITION_TAG_NAMES = {TAG_NAME_TOPIC, TAG_NAME_PARTITION};
    static final String[] TOPIC_PARTITION_OBJECT_TYPE_TAG_NAMES =
        {TAG_NAME_TOPIC, TAG_NAME_PARTITION, TAG_NAME_OBJECT_TYPE};

    private static final String RATE_DOC_PREFIX = "Rate of ";
    private static final String TOTAL_DOC_PREFIX = "Total number of ";
    private static final String AVG_DOC_PREFIX = "Average ";
    private static final String MAX_DOC_PREFIX = "Maximum ";
    private static final String BY_TOPIC_DOC_SUFFIX = " tagged by topic";
    private static final String BY_TOPIC_PARTITION_DOC_SUFFIX = " tagged by topic and partition";
    private static final String BY_OBJECT_TYPE_DOC_SUFFIX = " tagged by object type";
    private static final String BY_TOPIC_OBJECT_TYPE_DOC_SUFFIX = " tagged by topic and object type";
    private static final String BY_TOPIC_PARTITION_OBJECT_TYPE_DOC_SUFFIX = " tagged by topic, "
        + "partition and object type";

    // Segment copy metric names
    static final String SEGMENT_COPY = "segment-copy";
    static final String SEGMENT_COPY_TIME = SEGMENT_COPY + "-time";
    static final String SEGMENT_COPY_TIME_DOC = "time spent processing and uploading a log segment and indexes";
    static final String SEGMENT_COPY_TIME_AVG = SEGMENT_COPY_TIME + "-avg";

    final MetricNameTemplate segmentCopyTimeAvg = new MetricNameTemplate(
        SEGMENT_COPY_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_COPY_TIME_DOC
    );
    final MetricNameTemplate segmentCopyTimeAvgByTopic = new MetricNameTemplate(
        SEGMENT_COPY_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_COPY_TIME_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentCopyTimeAvgByTopicPartition = new MetricNameTemplate(
        SEGMENT_COPY_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_COPY_TIME_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );

    static final String SEGMENT_COPY_TIME_MAX = SEGMENT_COPY_TIME + "-max";

    final MetricNameTemplate segmentCopyTimeMax = new MetricNameTemplate(
        SEGMENT_COPY_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_COPY_TIME_DOC
    );
    final MetricNameTemplate segmentCopyTimeMaxByTopic = new MetricNameTemplate(
        SEGMENT_COPY_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_COPY_TIME_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentCopyTimeMaxByTopicPartition = new MetricNameTemplate(
        SEGMENT_COPY_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_COPY_TIME_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );

    // Segment delete metric names
    static final String SEGMENT_DELETE = "segment-delete";
    static final String SEGMENT_DELETE_RATE = SEGMENT_DELETE + "-rate";
    static final String SEGMENT_DELETE_DOC = "delete remote segment operations, including all its objects";
    final MetricNameTemplate segmentDeleteRequestsRate = new MetricNameTemplate(
        SEGMENT_DELETE_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_DOC
    );
    final MetricNameTemplate segmentDeleteRequestsRateByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteRequestsRateByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_TOTAL = SEGMENT_DELETE + "-total";
    final MetricNameTemplate segmentDeleteRequestsTotal = new MetricNameTemplate(
        SEGMENT_DELETE_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_DOC
    );
    final MetricNameTemplate segmentDeleteRequestsTotalByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteRequestsTotalByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_BYTES = SEGMENT_DELETE + "-bytes";
    static final String SEGMENT_DELETE_BYTES_DOC = "deleted number of bytes estimated from segment size";
    static final String SEGMENT_DELETE_BYTES_RATE = SEGMENT_DELETE_BYTES + "-rate";
    final MetricNameTemplate segmentDeleteBytesRate = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC
    );
    final MetricNameTemplate segmentDeleteBytesRateByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteBytesRateByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_BYTES_TOTAL = SEGMENT_DELETE_BYTES + "-total";
    final MetricNameTemplate segmentDeleteBytesTotal = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC
    );
    final MetricNameTemplate segmentDeleteBytesTotalByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteBytesTotalByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_TIME = SEGMENT_DELETE + "-time";
    static final String SEGMENT_DELETE_TIME_DOC = "time spent deleting log segment and indexes";
    static final String SEGMENT_DELETE_TIME_AVG = SEGMENT_DELETE_TIME + "-avg";
    final MetricNameTemplate segmentDeleteTimeAvg = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC
    );
    final MetricNameTemplate segmentDeleteTimeAvgByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteTimeAvgByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_AVG,
        METRIC_GROUP,
        AVG_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_TIME_MAX = SEGMENT_DELETE_TIME + "-max";
    final MetricNameTemplate segmentDeleteTimeMax = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC
    );
    final MetricNameTemplate segmentDeleteTimeMaxByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteTimeMaxByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_TIME_MAX,
        METRIC_GROUP,
        MAX_DOC_PREFIX + SEGMENT_DELETE_TIME_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_ERRORS = SEGMENT_DELETE + "-errors";
    static final String SEGMENT_DELETE_ERRORS_DOC = "errors during remote log segment deletion";
    static final String SEGMENT_DELETE_ERRORS_RATE = SEGMENT_DELETE_ERRORS + "-rate";
    final MetricNameTemplate segmentDeleteErrorsRate = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC
    );
    final MetricNameTemplate segmentDeleteErrorsRateByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteErrorsRateByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    static final String SEGMENT_DELETE_ERRORS_TOTAL = SEGMENT_DELETE_ERRORS + "-total";
    final MetricNameTemplate segmentDeleteErrorsTotal = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC
    );
    final MetricNameTemplate segmentDeleteErrorsTotalByTopic = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentDeleteErrorsTotalByTopicPartition = new MetricNameTemplate(
        SEGMENT_DELETE_ERRORS_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_DELETE_ERRORS_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );

    // Segment fetch metric names
    static final String SEGMENT_FETCH = "segment-fetch";
    static final String SEGMENT_FETCH_REQUESTED_BYTES_DOC = "bytes requested by broker, "
        + "not necessarily the amount to be consumed by fetcher";
    static final String SEGMENT_FETCH_REQUESTED_BYTES = SEGMENT_FETCH + "-requested-bytes";
    static final String SEGMENT_FETCH_REQUESTED_BYTES_RATE = SEGMENT_FETCH_REQUESTED_BYTES + "-rate";
    final MetricNameTemplate segmentFetchRequestedBytesRate = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC
    );
    final MetricNameTemplate segmentFetchRequestedBytesRateByTopic = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES);
    final MetricNameTemplate segmentFetchRequestedBytesRateByTopicPartition = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES);
    static final String SEGMENT_FETCH_REQUESTED_BYTES_TOTAL = SEGMENT_FETCH_REQUESTED_BYTES + "-total";
    final MetricNameTemplate segmentFetchRequestedBytesTotal = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC
    );
    final MetricNameTemplate segmentFetchRequestedBytesTotalByTopic = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate segmentFetchRequestedBytesTotalByTopicPartition = new MetricNameTemplate(
        SEGMENT_FETCH_REQUESTED_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + SEGMENT_FETCH_REQUESTED_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );

    // Object upload metrics
    static final String OBJECT_UPLOAD = "object-upload";
    static final String OBJECT_UPLOAD_DOC = "upload to a storage backend operations";
    static final String OBJECT_UPLOAD_RATE = OBJECT_UPLOAD + "-rate";
    final MetricNameTemplate objectUploadRequestsRate = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC
    );
    final MetricNameTemplate objectUploadRequestsRateByTopic = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsRateByTopicPartition = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsRateByObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_OBJECT_TYPE_DOC_SUFFIX,
        OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsRateByTopicAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsRateByTopicPartitionAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_PARTITION_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_PARTITION_OBJECT_TYPE_TAG_NAMES
    );
    static final String OBJECT_UPLOAD_TOTAL = OBJECT_UPLOAD + "-total";
    final MetricNameTemplate objectUploadRequestsTotal = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_DOC
    );
    final MetricNameTemplate objectUploadRequestsTotalByTopic = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsTotalByTopicPartition = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsTotalByObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_OBJECT_TYPE_DOC_SUFFIX,
        OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsTotalByTopicAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_DOC_SUFFIX + " and object type",
        TOPIC_OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadRequestsTotalByTopicPartitionAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_TOTAL,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_DOC + BY_TOPIC_PARTITION_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_PARTITION_OBJECT_TYPE_TAG_NAMES
    );
    static final String OBJECT_UPLOAD_BYTES = OBJECT_UPLOAD + "-bytes";
    static final String OBJECT_UPLOAD_BYTES_DOC = "bytes uploaded to a storage backend";
    static final String OBJECT_UPLOAD_BYTES_RATE = OBJECT_UPLOAD_BYTES + "-rate";
    final MetricNameTemplate objectUploadBytesRate = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC
    );
    final MetricNameTemplate objectUploadBytesRateByTopic = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesRateByTopicPartition = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesRateByObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_OBJECT_TYPE_DOC_SUFFIX,
        OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesRateByTopicAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesRateByTopicPartitionAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_RATE,
        METRIC_GROUP,
        RATE_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_PARTITION_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_PARTITION_OBJECT_TYPE_TAG_NAMES
    );
    public static final String OBJECT_UPLOAD_BYTES_TOTAL = OBJECT_UPLOAD_BYTES + "-total";
    final MetricNameTemplate objectUploadBytesTotal = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC
    );
    final MetricNameTemplate objectUploadBytesTotalByTopic = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_DOC_SUFFIX,
        TOPIC_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesTotalByTopicPartition = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_PARTITION_DOC_SUFFIX,
        TOPIC_PARTITION_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesTotalByObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_OBJECT_TYPE_DOC_SUFFIX,
        OBJECT_TYPE_TAG_NAMES);
    final MetricNameTemplate objectUploadBytesTotalByTopicAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_OBJECT_TYPE_TAG_NAMES
    );
    final MetricNameTemplate objectUploadBytesTotalByTopicPartitionAndObjectType = new MetricNameTemplate(
        OBJECT_UPLOAD_BYTES_TOTAL,
        METRIC_GROUP,
        TOTAL_DOC_PREFIX + OBJECT_UPLOAD_BYTES_DOC + BY_TOPIC_PARTITION_OBJECT_TYPE_DOC_SUFFIX,
        TOPIC_PARTITION_OBJECT_TYPE_TAG_NAMES
    );

    public static String sensorName(final String name) {
        return name;
    }

    public static String sensorNameByObjectType(final ObjectKeyFactory.Suffix suffix, final String name) {
        return TAG_NAME_OBJECT_TYPE + "." + suffix.value + "." + name;
    }

    public static String sensorNameByTopic(final TopicPartition topicPartition, final String name) {
        return TAG_NAME_TOPIC + "." + topicPartition.topic() + "." + name;
    }

    public static String sensorNameByTopicAndObjectType(final TopicPartition topicPartition,
                                                        final ObjectKeyFactory.Suffix suffix,
                                                        final String name) {
        return TAG_NAME_TOPIC + "." + topicPartition.topic() + "."
            + TAG_NAME_OBJECT_TYPE + "." + suffix.value
            + "." + name;
    }

    public static String sensorNameByTopicPartition(final TopicPartition topicPartition, final String name) {
        return TAG_NAME_TOPIC + "." + topicPartition.topic()
            + "." + TAG_NAME_PARTITION + "." + topicPartition.partition()
            + "." + name;
    }

    public static String sensorNameByTopicPartitionAndObjectType(final TopicPartition topicPartition,
                                                                 final ObjectKeyFactory.Suffix suffix,
                                                                 final String name) {
        return TAG_NAME_TOPIC + "." + topicPartition.topic()
            + "." + TAG_NAME_PARTITION + "." + topicPartition.partition()
            + "." + TAG_NAME_OBJECT_TYPE + "." + suffix.value
            + "." + name;
    }

    static Map<String, String> topicTags(final TopicPartition topicPartition) {
        return Map.of(TAG_NAME_TOPIC, topicPartition.topic());
    }

    static Map<String, String> topicAndObjectTypeTags(final TopicPartition topicPartition,
                                                      final ObjectKeyFactory.Suffix suffix) {
        return Map.of(
            TAG_NAME_TOPIC, topicPartition.topic(),
            TAG_NAME_OBJECT_TYPE, suffix.value
        );
    }

    static Map<String, String> topicPartitionTags(final TopicPartition topicPartition) {
        return Map.of(
            TAG_NAME_TOPIC, topicPartition.topic(),
            TAG_NAME_PARTITION, String.valueOf(topicPartition.partition())
        );
    }

    static Map<String, String> topicPartitionAndObjectTypeTags(final TopicPartition topicPartition,
                                                               final ObjectKeyFactory.Suffix suffix) {
        return Map.of(
            TAG_NAME_TOPIC, topicPartition.topic(),
            TAG_NAME_PARTITION, String.valueOf(topicPartition.partition()),
            TAG_NAME_OBJECT_TYPE, suffix.value
        );
    }

    static Map<String, String> objectTypeTags(final ObjectKeyFactory.Suffix suffix) {
        return Map.of(TAG_NAME_OBJECT_TYPE, suffix.value);
    }

    public Iterable<MetricNameTemplate> all() {
        return List.of(
            // segment copy
            segmentCopyTimeAvg,
            segmentCopyTimeAvgByTopic,
            segmentCopyTimeAvgByTopicPartition,
            segmentCopyTimeMax,
            segmentCopyTimeMaxByTopic,
            segmentCopyTimeMaxByTopicPartition,
            // segment delete
            segmentDeleteRequestsRate,
            segmentDeleteRequestsRateByTopic,
            segmentDeleteRequestsRateByTopicPartition,
            segmentDeleteRequestsTotal,
            segmentDeleteRequestsTotalByTopic,
            segmentDeleteRequestsTotalByTopicPartition,
            segmentDeleteBytesTotal,
            segmentDeleteBytesTotalByTopic,
            segmentDeleteBytesTotalByTopicPartition,
            segmentDeleteTimeAvg,
            segmentDeleteTimeAvgByTopic,
            segmentDeleteTimeAvgByTopicPartition,
            segmentDeleteTimeMax,
            segmentDeleteTimeMaxByTopic,
            segmentDeleteTimeMaxByTopicPartition,
            segmentDeleteErrorsRate,
            segmentDeleteErrorsRateByTopic,
            segmentDeleteErrorsRateByTopicPartition,
            segmentDeleteErrorsTotal,
            segmentDeleteErrorsTotalByTopic,
            segmentDeleteErrorsTotalByTopicPartition,
            // segment fetch
            segmentFetchRequestedBytesRate,
            segmentFetchRequestedBytesRateByTopic,
            segmentFetchRequestedBytesRateByTopicPartition,
            segmentFetchRequestedBytesTotal,
            segmentFetchRequestedBytesTotalByTopic,
            segmentFetchRequestedBytesTotalByTopicPartition,
            // object upload
            objectUploadRequestsRate,
            objectUploadRequestsRateByTopic,
            objectUploadRequestsRateByTopicPartition,
            objectUploadRequestsRateByObjectType,
            objectUploadRequestsRateByTopicAndObjectType,
            objectUploadRequestsRateByTopicPartitionAndObjectType,
            objectUploadRequestsTotal,
            objectUploadRequestsTotalByTopic,
            objectUploadRequestsTotalByTopicPartition,
            objectUploadRequestsTotalByObjectType,
            objectUploadRequestsTotalByTopicAndObjectType,
            objectUploadRequestsTotalByTopicPartitionAndObjectType,
            objectUploadBytesRate,
            objectUploadBytesRateByTopic,
            objectUploadBytesRateByTopicPartition,
            objectUploadBytesRateByObjectType,
            objectUploadBytesRateByTopicAndObjectType,
            objectUploadBytesRateByTopicPartitionAndObjectType,
            objectUploadBytesTotal,
            objectUploadBytesTotalByTopic,
            objectUploadBytesTotalByTopicPartition,
            objectUploadBytesTotalByObjectType,
            objectUploadBytesTotalByTopicAndObjectType,
            objectUploadBytesTotalByTopicPartitionAndObjectType
        );
    }
}
