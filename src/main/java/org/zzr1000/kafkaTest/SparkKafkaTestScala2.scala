package org.zzr1000.kafkaTest

/*
Receiver模式：KafkaUtils.createStream()

Receiver方式通过KafkaUtils.createStream（）方法来创建一个DStream对象，
它不关注消费位移的处理
但这种方式在Spark任务执行异常时会导致数据丢失，
如果要保证数据的可靠性，则需要开启预写式日志，简称WAL（WriteAheadLogs），
只有收到的数据被持久化到WAL之后才会更新Kafka中的消费位移。
收到的数据和WAL存储位置信息被可靠地存储，
如果期间出现故障，那么这些信息被用来从错误中恢复，并继续处理数据。

WAL的方式可以保证从Kafka中接收的数据不被丢失。
但是在某些异常情况下，一些数据被可靠地保存到了WAL中，但是还没有来得及更新消费位移，
这样会造成Kafka中的数据被Spark拉取了不止一次。

同时在Receiver方式中，Spark的RDD分区和Kafka的分区并不是相关的，
因此增加Kafka中主题的分区数并不能增加Spark处理的并行度，仅仅增加了接收器接收数据的并行度。

 */

/*
Director模式：KafkaUtils.createDirectStream()
Direct方式是从Spark1.3开始引入的，
它通过KafkaUtils.createDirectStream（）方法创建一个DStream对象，
该方式中Kafka的一个分区与SparkRDD对应，
通过定期扫描所订阅的Kafka每个主题的每个分区的最新偏移量以确定当前批处理数据偏移范围。
与Receiver方式相比，Direct方式不需要维护一份WAL数据，
由SparkStreaming程序自己控制位移的处理，通常通过检查点机制(checkpoint)处理消费位移，
这样可以保证Kafka中的数据只会被Spark拉取一次。

注意使用Direct的方式并不意味着实现了精确一次的语义（ExactlyOnceSemantics），
如果要达到精确一次的语义标准，则还需要配合幂等性操作或事务性操作。
 */
object SparkKafkaTestScala2 {

}
