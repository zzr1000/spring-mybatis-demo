package org.zzr1000.threadTest;


/**
 public interface Executor {
 void execute(Runnable command);
 } 该接口表示：可以执行一个任务：Runnable表示要具体做的事情，是一个函数式接口，可以使用lambda表达式实现

 该接口有一个重要实现接口：ExecutorService：Executors中每一个方法，返回的都是ExecutorService
 (其实也是Executor了，所以Executors方法中的每一个方法，返回的都是Executor：Executor表示执行一个任务)

 Executors中的每一个方法：都是通过 ThreadPoolExecutor 方法来实现的:

 自定义线程池，也可以通过调用  ThreadPoolExecutor 方法实现，如果现有线程池，已满足需求，则可以直接使用Executors中的方法

 ThreadPoolExecutor也是继承自ExecutorService，最终继承自Executor，只不过 ThreadPoolExecutor 是 pool 的概念了


 总结下：

 Executor---->ExecutorService---->ThreadPoolExecutor(池化)

 Executors类中的每一个方法，返回的都是ExecutorService(Executor)：原因：

 Executors类中的每一个方法，都是通过 new ThreadPoolExecutor 类实现的

 总结：

 Executors类中的每一个方法，返回的，都是Executor，之所以返回Executor，是因为，每个方法调用的，都是Executor的实现类ThreadPoolExecutor

 就是这种关系：......


 Executors类中的每一个方法，可以传入一个自定义的ThreadFactory类，指定一些线程的属性；如果不指定，则使用默认的实现DefaultThreadFactory


 */

public class Detail {
}
