package org.zzr1000.threadTest.executorTest;


/**
 * refer to :
 * https://blog.csdn.net/chenchaofuck1/article/details/51589774
 *
 * 一句话总结：
 * Executors中的每一个方法都是返回的Executor（ExecutorService），原因是：每个方法，都是通过new
 * ThreadPoolExecutor 类返回的：
 *
 * 在new ThreadPoolExecutor时，可以指定ThreadFactory：
 *
 * 就是这个关系：. . .
 *
 *
 */
public class ThreadFactoryTest {
}


