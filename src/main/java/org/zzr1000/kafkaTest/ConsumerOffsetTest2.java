package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

public class ConsumerOffsetTest2 {

    public static final String brokerList = "xxx:9092";
    public static final String topic = "test";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Collections.singletonList(topic));

        Set<TopicPartition> assignment = new HashSet();

        while (assignment.size() == 0){
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }

        // 各partition，分别返回，返回一个Map：
        // endOffsets从分区末尾消费
        Map<TopicPartition,Long> offsets = consumer.endOffsets(assignment);
        // beginningOffsets不一定始终为0：当日志被清理机制清理之后，beginningOffsets会有变化
        Map<TopicPartition,Long> offsetsBegin = consumer.beginningOffsets(assignment);

        for(TopicPartition tp : assignment){
            consumer.seek(tp,offsets.get(tp));
        }

        //从消息的头部或尾部消费的简洁写法：..
        consumer.seekToBeginning(assignment);
        consumer.seekToEnd(assignment);
    }
}


