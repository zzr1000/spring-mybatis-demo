package org.zzr1000.threadTest.executorTest;

import org.junit.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Java通过Executors提供四种线程池，分别为：
newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */

public class ExecutorTest3 {


    //1、newCachedThreadPool
    //线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
    @Test
    public void test(){

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for(int i = 0; i < 10 ; i++ ){
            final int index = i;
            try{
                Thread.sleep(index * 1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(index);
                }
            });
        }

    }

    //2、newFixedThreadPool
    //创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    //因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
    //定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
    @Test
    public void test2(){
      //System.out.println(Runtime.getRuntime().availableProcessors());//8
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for(int i = 0; i < 10 ; i++ ){
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println(index);
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //3、newScheduledThreadPool
    @Test
    public void test3(){

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 3 secondes");
            }
        },1,3, TimeUnit.SECONDS);

    }
}
