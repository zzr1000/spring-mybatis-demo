package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

//根据时间获得offset
public class ConsumerOffsetTest3 {

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

        //consumer分配到的TopicPartition
        Set<TopicPartition> assignment = new HashSet();
        //TopicPartition对应的offset
        Map<TopicPartition,Long> timestampToSearch = new HashMap<>();

        //直到分配到partition，不停poll：不停poll，直到分配到消费的partition
        while (assignment.size() == 0){
            consumer.poll(Duration.ofMillis(100));
            assignment = consumer.assignment();
        }

        for(TopicPartition tp : assignment){
            timestampToSearch.put(tp,System.currentTimeMillis() - 1*24*3600*1000);
        }

        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(timestampToSearch);

        for(TopicPartition tp : assignment) {

            OffsetAndTimestamp offsetAndTimestamp = offsets.get(tp);

            if (offsetAndTimestamp != null){
                consumer.seek(tp,offsetAndTimestamp.offset());
            }

        }

    }
}


