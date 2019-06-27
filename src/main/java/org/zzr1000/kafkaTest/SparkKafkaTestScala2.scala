package org.zzr1000.kafkaTest

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.{SparkConf, TaskContext}
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.LocationStrategies._
import org.apache.spark.streaming.kafka010.ConsumerStrategies._


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

/*
Direct方式下，SparkStreaming会自己控制消费位移的处理，
那么原本应该保存到Kafka中的消费位移就无法提供准确的信息了。
但是在某些情况下，比如监控需求，我们又需要获取当前SparkStreaming正在处理的消费位移。
SparkStreaming也考虑到了这种情况，可以通过下面的程序来获取消费位移：
 */
object SparkKafkaTestScala2 {

  private val brokers = "xxxxxx:9092"
  private val topic = "test"
  private val group = "t0628"
  private val checkPoint = "/root/v/spark/checkpoint"

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[1]").setAppName("Test")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.checkpoint(checkPoint)

    val kafkaParam = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokers,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.GROUP_ID_CONFIG -> group,
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> "latest",
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> (false:java.lang.Boolean)
    )

    /*
    从Kafka中消费数据，这里的SparkStreaming本质上是一个消费者，
    因此KafkaUtils.createDirectStream()方法也需要指定KafkaConsumer的相关配置。
    KafkaUtils.createDirectStream()方法的第一个参数好理解，
    方法中的第二个参数是LocationStrategies类型的，
        用来指定Spark执行器节点上KafkaConsumer的分区分配策略。
        LocationStrategies类型提供了3种策略：
            PerferBrokers策略，必须保证执行器节点和KafkaBroker拥有相同的host，即两者在相同的机器上，这样可以根据分区副本的leader节点来进行分区分配；
            PerferConsistent策略，该策略将订阅主题的分区均匀地分配给所有可用的执行器，在绝大多数情况下都使用这种策略，本示例使用的也是这种策略；
            PerferFixed策略，允许开发人员指定分区与host之间的映射关系。
    KafkaUtils.createDirectStream()方法中的第三个参数是ConsumerStrategies类型的，
        用来指定Spark执行器节点的消费策略。
        与KafkaConsumer订阅主题的方式对应，这里也有3种策略：
        Subscribe、SubscribePattern和Assign，
        分别代表通过指定集合、通过正则表达式和通过指定分区的方式进行订阅。
     */

    val stream = KafkaUtils
      .createDirectStream(
        ssc,
        PreferConsistent,
        Subscribe[String, String](List(topic), kafkaParam)
      )

    /*
    创建DStream之后，第一个调用
     */
    stream.foreachRDD(rdd => {
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd.foreachPartition{
        iter => val o:OffsetRange = offsetRanges(TaskContext.getPartitionId())
        println(s"${o.topic}${o.partition}${o.fromOffset}${o.untilOffset}")
      }
    })

    val value = stream.map(record => {
      val intVal = Integer.valueOf(record.value())
      print(intVal)
      intVal
    }).reduce(_ + _)

    value.print()

    ssc.start()
    ssc.awaitTermination()

  }
}
