package org.zzr1000.threadTest.executorTest;

import java.util.concurrent.Executor;

// refer to:
// https://www.toutiao.com/i6706248303886991875/
// https://www.toutiao.com/c/user/1545649032/#mid=51569509184
// 底层线程操作存在的问题：
// 1、低级的并发原语，比如synchronized和wait、notify等很难被正确使用，从而导致竞态条件、线程饿死、死锁和其他风险。
// 2、太过依赖synchronized原语存在性能问题，也会影响程序的课扩展性，对于诸如Web服务器之类的高度线程化的程序而言，后果很严重。
// 3、开发者需要高级的线程结构，如线程池和信号量。需要自己构建，不仅耗时而且容易出错。
// 基于以上问题，Java 5 引入了并发工具类，由强大且容易扩展的高性能工具类组成，包含线程池和阻塞队列。
// java.util.concurrent: 并发编程工具类，如Executors
// java.util.concurrent.atomic: 支持单个变量无所且线程安全操作
// java.util.concurrent.locks: 在某些条件下获取锁和执行等待的工具类。虽然Java可以通过监听器来实现同步、等待以及唤醒机制，但这些类型具有更好的性能和伸缩性。
// 可以通过java.long.System.nanoTime()访问纳秒级别的时间资源。
// 并发工具类可以分为executor、同步器（synchronizer）以及锁框架等。
public class ExecutorTest {

    public static void main(String[] args) {
        Executor executor = command -> System.out.println("command");
        executor.execute(new RunnableTask());
    }
}

class RunnableTask implements Runnable {
        @Override
        public void run() {
            System.out.println("task run");
        }
}
