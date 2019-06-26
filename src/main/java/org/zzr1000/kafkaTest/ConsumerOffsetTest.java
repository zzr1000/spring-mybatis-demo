package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import sun.plugin.javascript.navig.Array;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConsumerOffsetTest {

    public static final String brokerList = "xxxxx";
    public static final String topic = "test";
    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers",brokerList);
        properties.put("group.id",groupId);
        properties.put("client.id","consumer-test");

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        TopicPartition tp = new TopicPartition(topic,0);
        consumer.assign(Arrays.asList(tp));
        long lastConsumedOffset = -1;

        while (true){

            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));

            List<ConsumerRecord<String,String>> partitionRecords = records.records(tp);

            //批次消息不为空的时候，可以验证，否则，会报异常：
            //可以使用get方法，获取批次消息中的对应位置消息，然后，还可以获得这个消息中，本身自带的offset信息
            //lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();

            for(ConsumerRecord<String,String> record:partitionRecords){
                System.out.println(
                        "topic:"+record.topic()
                                +"==key:"+record.key()
                                +"==partition:"+record.partition()
                                +"==offset:"+record.offset()
                                +"==value:"+record.value());
            }

            consumer.commitSync();

            //System.out.println(lastConsumedOffset);
            //System.out.println(consumer.committed(tp));//OffsetAndMetadata{offset=14, leaderEpoch=0, metadata=''}
            //System.out.println(consumer.position(tp));//消费者当前位置:14
            System.out.println(partitionRecords.size());//这一批次的消息数量

        }

    }

}
