package org.zzr1000.kafkaTest;

// refer to:
// https://www.cnblogs.com/intsmaze/p/6709297.html
// https://www.oschina.net/news/107779/kafka-2-3-released

/*
====0.8.2:
producer：所有请求都是异步发送：不在区分同步、异步：producer请求会返回一个应答对象，包括offset或者错误信息（两者互斥）
新的producer和所有的服务器网络通信都是异步地，在ack=-1模式下需要等待所有的replica副本完成复制时，可以大幅减少等待时间。

在0.8.2之前，comsumer定期提交已经消费的kafka消息的offset位置到zookeeper中保存。
对zookeeper而言，每次写操作代价是很昂贵的，而且zookeeper集群是不能扩展写能力的。
在0.8.2开始，可以把comsumer提交的offset记录在compacted topic（__comsumer_offsets）中，
该topic设置最高级别的持久化保证，即ack=-1。
__consumer_offsets由一个三元组< comsumer group, topic, partiotion> 组成的key和offset值组成，
在内存也维持一个最新的视图view，所以读取很快。

kafka可以频繁的对offset做检查点checkpoint，即使每消费一条消息提交一次offset。

====0.9:
安全：在scyd已有了解：. .
Kafka Connect：之前版本没有这个概念：
offset自动管理
新的Comsumer API：新的Comsumer API不再有high-level、low-level之分了，而是自己维护offset。
    这样做的好处是避免应用出现异常时，数据未消费成功，但Position已经提交，导致消息未消费的情况发生。
    通过查看API，新的Comsumer API有以下功能：
　　      Kafka可以自行维护Offset、消费者的Position。也可以开发者自己来维护Offset，实现相关的业务需求。
　　      消费时，可以只消费指定的Partitions(assign)
　　      可以使用外部存储记录Offset，如数据库之类的。（存db）
　　      自行控制Consumer消费消息的位置。(seek)
　　      可以使用多线程进行消费（多线程消费）

====0.10:
机架感知
消息中包含时间戳字段
新的安全特性
kafka connect 提升：
Kafka Consumer Max Records：
    在Kafka 0.9.0.0，开发者们在新consumer上使用poll()函数的时候是几乎无法控制返回消息的条数。
    不过值得高兴的是，此版本的Kafka引入了max.poll.records参数，允许开发者控制返回消息的条数。
协议版本改进：
    Kafka brokers现在支持返回所有支持的协议版本的请求API，这个特点的好处就是以后将允许一个客户端支持多个broker版本。

====0.11:
从这个版本开始支持"exactly-once"语义
一、修改unclean.leader.election.enabled默认值为false：
二、确保offsets.topic.replication.factor参数被正确应用：
三、优化了对Snappy压缩的支持
四、消息增加头部信息(Header)：Record增加了Header，每个header是一个KV存储。具体的header设计参见KIP-82
五、空消费者组延时rebalance
六、消息格式变更：增加最新的magic值；增加了header信息。
            同时为了支持幂等producer和EOS，增加一些与事务相关的字段，使得单个record数据结构体积增加。
            但因为优化了RecordBatch使得整个batch所占体积反而减少，进一步降低了网络IO开销。
七、新的分配算法：StickyAssignor：
八、controller重设计
九、支持EOS：0.11最重要的功能：
        EOS是流式处理实现正确性的基石。
        主流的流式处理框架基本都支持EOS（如Storm Trident, Spark Streaming, Flink），Kafka streams肯定也要支持的。
        0.11版本通过3个大的改动支持EOS：1.幂等的producer（这也是千呼万唤始出来的功能）；
                                    2. 支持事务；
                                    3. 支持EOS的流式处理(保证读-处理-写全链路的EOS)
 */




public class KafakaVersion {

    public static void main(String[] args) {

    }

}
