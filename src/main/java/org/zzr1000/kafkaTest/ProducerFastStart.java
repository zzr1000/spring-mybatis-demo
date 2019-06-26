package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;


import java.util.Properties;

public class ProducerFastStart {

    static {
        PropertyLoadTest.initProperties("");
    }
    public static final String brokerList = PropertyLoadTest.getConfigValue("brokerList");
    public static final String topic = "test";

    public static void main(String[] args) {

        Properties properties = new Properties();

        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers",brokerList);

      //上述三个配置，可优化为此写法
      //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      //properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
      //properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);


        //配置生产者客户端参数并创建KafkaProducer实例
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        //构建要发送的消息
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"hi2,from java client");

        producer.send(record);

        producer.close();
    }

}
