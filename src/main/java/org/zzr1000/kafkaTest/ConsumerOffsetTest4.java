package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;

//offset保存到db：
public class ConsumerOffsetTest4 {

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

        for(TopicPartition tp : assignment){
            //long offset = getOffsetFromDB(tp);//从db中读取offset
        }

        while (true){
            ConsumerRecords<String,String>records=consumer.poll(Duration.ofMillis(1000));

            for(TopicPartition partition:records.partitions()){
                List<ConsumerRecord<String,String>>partitionRecords=records.records(partition);
                for(ConsumerRecord<String,String>record:partitionRecords){
                    //process
                }
                long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                //storeOffsetToDB(partition,lastConsumedOffset);
            }
        }
    }
}


