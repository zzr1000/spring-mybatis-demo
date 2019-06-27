package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

//KafkaProducer 线程安全、Consumer线程不安全
/*
线程不安全，并不意味着，不能多线程执行，也可以多线程执行：
1、线程封闭：
    为每一个线程实例化一个KafkaConsumer
2、多个消费线程同时消费同一个分区：
    这种可以打破消费线程数不能超过分区数的限制，但控制较复杂，不常使用
 */
public class ThreadTest {

    public static final String brokerList = "xxx:9092";
    public static final String topic = "test";
    public static final String groupId = "group.demo";

    public static Properties initConfig(){
        Properties prop = new Properties();
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);

        return prop;
    }

    //线程数，可以设置为topic的partiton数
    //如果不知道topic的partition数目，可以使用KafkaConsumer的
    //partitionsFor()方法，获取topic的partition数，
    //进而设置合理的thread数目
    //该方法，和开启多个消费进程方法相同
    public static void main(String[] args) {
        Properties prop = initConfig();
        int consumerThreadNum = 4;
        for(int i = 0 ; i < consumerThreadNum ; i++){
            new KafkaConsumerThread(prop,topic).start();
        }
    }

    public static class KafkaConsumerThread extends Thread{
        private KafkaConsumer<String,String > kafkaConsumer;

        public KafkaConsumerThread(Properties prop,String topic){
            this.kafkaConsumer = new KafkaConsumer<String, String>(prop);
            this.kafkaConsumer.subscribe(Arrays.asList(topic));
        }

        public void run(){
            try {
                while (true) {
                    ConsumerRecords<String, String> records =
                            kafkaConsumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record : records) {
                        //process
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                kafkaConsumer.close();
            }
        }

    }
}
