package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class ConsumerOffsetSeek {

    public static final String brokerList = "xxx:9092";
    public static final String topic = "test";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers",brokerList);
        properties.put("group.id",groupId);

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Collections.singletonList(topic));

        //先要poll一下，分配到消费分区
        consumer.poll(Duration.ofMillis(1000));

        //获得消费分区
        Set<TopicPartition> tp = consumer.assignment();

        //对所有消费分区或者特定分区，进行seek具体offset
        for (TopicPartition t : tp){
            consumer.seek(t,3);
        }

        while (true){
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String,String> record: records){
                System.out.println(record.value());
            }
        }

    }
}
