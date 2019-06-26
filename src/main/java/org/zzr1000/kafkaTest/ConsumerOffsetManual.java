package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerOffsetManual {


    public static final String brokerList = "xxxxx";
    public static final String topic = "test";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", brokerList);
        properties.put("group.id", groupId);
        properties.put("client.id", "consumer-test");
        //手动管理offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        consumer.subscribe(Collections.singletonList(topic));

        while (true){

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

            for(ConsumerRecord<String,String> record : records){
                System.out.println(
                        "topic:"+record.topic()
                        +"==key:"+record.key()
                        +"==partition:"+record.partition()
                        +"==offset:"+record.offset()
                        +"==value:"+record.value());
            }

            //这个指定的位置：这种是最简单的方式：
            //还可以多个批次数据之后，大于某一值之后，在执行该语句 ，从而实现了，对提交的手动控制
            //但是这种方式，和把offset写入到mysql等外部存储，还是不同，还不是一个思路：...
            consumer.commitSync();

        }
    }
}
