package org.zzr1000.threadTest.executorTest3;

import org.zzr1000.todo.themeleafTest.ThemeleafTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*refer to : https://www.toutiao.com/a6707408843166450188

ExecutorService是Java提供的用于管理线程池的类。
该类的两个作用：控制线程数量和重用线程

具体的4种常用的线程池：（返回值都是ExecutorService）

2.1 Executors.newCacheThreadPool()：
可缓存线程池，先查看池中有没有以前建立的线程，如果有，就直接使用。
如果没有，就建一个新的线程加入池中，缓存型池子通常用于执行一些生存期很短的异步型任务
线程池为无限大，当执行当前任务时上一个任务已经完成，会复用执行上一个任务的线程，而不用每次新建线程

2.2 Executors.newFixedThreadPool(int n)：
创建一个可重用固定个数的线程池，以共享的无界队列方式来运行这些线程。
定长线程池的大小最好根据系统资源进行设置。
如Runtime.getRuntime().availableProcessors()
 */
public class ExecutoreTest {


    public static void main(String[] args) throws InterruptedException {
      //cacheThreadPool();
      //fixedThreadPool();
      //scheduledThreadPool1();
      //scheduledThreadPool2();
        singleThreadPool();
    }

    private static void cacheThreadPool() throws InterruptedException {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for(int i = 0 ; i <  10 ; i++){
            //sleep有助于观察使用的以前的线程
            Thread.sleep(1000);
            cacheThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()+" : runing .."));
        }
    }

    //因为线程池大小为3，每个任务输出打印结果后sleep 2秒，所以每两秒打印3个结果。
    private static void fixedThreadPool() throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for(int i = 0 ; i <  10 ; i++){
            //sleep有助于观察使用的以前的线程
            Thread.sleep(1000);
            fixedThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()+" : runing .."));
        }
    }

    //延迟10s执行
    private static void scheduledThreadPool1(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(
                ()-> System.out.println(Thread.currentThread().getName()+" : 延迟10s执行"),
                10,
                TimeUnit.SECONDS
        );
    }
    //延迟10s执行
    private static void scheduledThreadPool2(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(
                ()-> System.out.println(Thread.currentThread().getName()+" : 延迟10s后每3s周期执行一次"),
                10,
                3,
                TimeUnit.SECONDS
        );
    }

    private static void singleThreadPool() throws InterruptedException {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for(int i = 0 ; i <  10 ; i++){
            final int index = i;
            singleThreadPool.execute(()->{
                System.out.println(Thread.currentThread().getName()+":"+index);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
