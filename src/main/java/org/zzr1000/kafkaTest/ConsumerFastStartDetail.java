package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

public class ConsumerFastStartDetail {

    public static final String brokerList = "xxxx";
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

        //一个消费者可以订阅一个或者多个topic：以集合形式或者是正则表达式的形式
        //正则表达式：订阅之后，创建了新的符合条件的topic，消费者会增加消费
        //kafka和其他系统之间复制数据的时候，这种写法，很有效：..
        consumer.subscribe(Collections.singletonList(topic));
        consumer.subscribe(Pattern.compile("topic-*"));

        //消费者还可以订阅特定主体的特定分区：
        consumer.assign(Arrays.asList(new TopicPartition(topic,0)));

        //获得topic的partition信息：
        List<TopicPartition> partitions = new ArrayList<>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        if(partitionInfos != null){
            for(PartitionInfo partitionInfo : partitionInfos){
                partitions.add(new TopicPartition(partitionInfo.topic(),partitionInfo.partition()));
            }
        }
        consumer.assign(partitions);

        while (true){
            //poll设置时间：表示控制poll阻塞的时间：
            //在消费者缓冲区，没有可用数据时，会发生阻塞。
            //设置的时间，就表示，阻塞等待的时间：等到缓冲区有数据：..
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println(records.count());//一次拉取消息集的消息个数

            //打印如下值：
            //topic:test==key:null==offset:10==value:ddddddddddddddddddd
            //通过这个可以看到：对offset这个值，是非常容易获取或处理的：........
            for (ConsumerRecord<String,String> record: records){
                System.out.println(
                        "topic:"+record.topic()
                        +"==key:"+record.key()
                        +"==partition:"+record.partition()
                        +"==offset:"+record.offset()
                        +"==value:"+record.value());
                //do sth to process record
            }

            //上述实现方式为：获取消息集中的每一个ConsumerRecord
            //此外，还可以按照分区维度进行消费：这一点很有用，在手工提交位移的时候尤为明显
            //ConsumerRecords类提供了一个records(TopicPartition)方法来获取
            //消息集中指定分区的消息：传入TopicPartition信息，返回对应TopicPartition的记录
            for (TopicPartition tp: records.partitions()){//获得records的所有分区信息
                for(ConsumerRecord<String,String> record:records.records(tp)){//对每个分区信息中的信息，进行轮询处理
                    System.out.println(
                            "topic:"+record.topic()
                                    +"==key:"+record.key()
                                    +"==offset:"+record.offset()
                                    +"==value:"+record.value());
                    //do sth to process record
                }
            }

            //此外还可以按topic进行消息的处理：当一个消费者订阅多个topic的时候：..

        }
    }
}
