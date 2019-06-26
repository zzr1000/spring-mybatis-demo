package org.zzr1000.kafkaTest;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;


import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProducerFastStart {

    static {
        PropertyLoadTest.initProperties("");
    }
    public static final String brokerList = PropertyLoadTest.getConfigValue("brokerList");
    public static final String topic = "test";

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        Properties properties = new Properties();

        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers",brokerList);

      //上述三个配置，可优化为此写法
      //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      //properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
      //properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);

        //序列化方法：有多种类型，都实现了同样的接口
        //如果自带的不能满足需求，可以使用avro、json、thrift、pb等通用序列化工具；或自己实现对应序列化方法
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());

        //可以指定自定义的分区实现类：
        //消息在send之后，可能会经过拦截器(非必须)、序列化器(必需)、分区器(
        // 如果消息ProducerRecord中指定了partition字段，那么就不需要分区器，
        // 因为partition代表的就是要发往的分区号；如果ProducerRecord中没有
        // 指定partition字段，就需要依赖分区器：根据key字段计算partition的值)：
        // 想到了之前，flume向kafka写数据：导致partition数据不均匀的处理方法：
        //当前flume发送kafka数据中，没有设置记录的key，采用默认分发策略，每隔10分钟随机选择一个kafka partition 发送数据，这样可能会导致数据发送不均匀；
        //因此当前通过增加配置，在记录中增加一个uuid字段，通过uuid字段来进行hash分发，尽量使得数据均匀发送到不同的partition：。
        //关键是key有没有值：. .
        //默认分区器原理：如果key不为null，对key哈希，根据hash值计算分区号；【所有分区中的一个】
        //             如果key为null，那么消息将会以轮询的方式发往主题内的可用分区【可用分区中的一个】
        //不改变分区数目的情况下，key和分区的映射关系是不变的，一旦增加了分区，就不能保证该映射关系了：..
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class.getName());


        properties.put("ack","1");
        properties.put(ProducerConfig.ACKS_CONFIG,"1");

        //可重试异常，重试次数：
        properties.put(ProducerConfig.RETRIES_CONFIG,10);

        //配置生产者客户端参数并创建KafkaProducer实例
        KafkaProducer<String,String> producer = new KafkaProducer<String, String>(properties);

        //构建要发送的消息
        ProducerRecord<String,String> record = new ProducerRecord<>(topic,"hi2,from java client");

        // 1、fire-and-forget
        producer.send(record);

        // 2.1、sync
        // send方法本身异步，返回的Future对象可以使调用方稍后获得发送的结果
        // get()方法用来阻塞等待Kafka的响应，直到消息发送成功，或者发生异常
        // 如果发生异常需要捕获异常，并交由外部逻辑处理
        producer.send(record).get();

        // 2.2、sync
        // 如果需要record发送之后的topic、partition等信息，则使用如下方法
        // 如果不需要，则使用2.1方法：..
        Future<RecordMetadata> future = producer.send(record);
        RecordMetadata metadata = future.get();
        System.out.println(metadata.topic() + "-" +
                           metadata.partition() + "-" +
                           metadata.offset());

        // 2.3、可超时的阻塞：可以解决一些可重试的异常信息：比如：
        // NetWorkException 、LeaderNotAvailableException等
        // 重试次数，可以在property中设置：
        // properties.put(ProducerConfig.RETRIES_CONFIG,10);
        producer.send(record).get(1000, TimeUnit.MILLISECONDS);

        // 2：同步发送方式总结：
        // 可靠性高，要么消息发送成功、要么发生异常，如果发生异常，则可以捕获异常，进行处理
        // 性能低：需要阻塞等待一条消息发送完成之后，在发送下一条消息：. .


        // 3、async：异步方式：
        // 在send方法中指定一个callback回调函数，kafka在 返回响应 时调用该函数实现异步的发送确认
        // 疑问：send返回类型是Future，本身就可以用作异步的逻辑处理，这样做不是不行
        // 但是Future里的get方法在何时调用，以及怎么调用，都需要处理，消息不停发送，诸多消息对应的Future
        // 对象的处理，会使代码逻辑混乱；使用callback方式就非常简单明了：
        // kafka有响应时，就回调，要么发送成功，要么抛出异常

        // 示例代码中，处理异常，只是打印，实际生产中，可以做其他记录，或者做重发操作
        // onCompletion两个参数互斥：
        // 发送成功的时候，recordMetadata不为null，Exception为null；发送失败时候，反之

        // 回调函数的调用，可以保证分区之间有序
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    e.printStackTrace();
                }else {
                    System.out.println(
                            metadata.topic() + "-" +
                            metadata.partition() + "-" +
                            metadata.offset());
                }
            }
        });

        //回收资源：
        //close方法，会阻塞等待之前所有的发送请求处理完成后再关闭producer
        //还有一个带超时时间的close方法：只会等待对应时间，然后强制退出
        //实际生产中，不使用带超时时间的close方法：..
        producer.close();
        producer.close(1000,TimeUnit.MILLISECONDS);
    }

}
