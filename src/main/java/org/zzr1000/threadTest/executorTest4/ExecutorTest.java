package org.zzr1000.threadTest.executorTest4;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// refer to :
// https://www.cnblogs.com/laoyeye/p/6589150.html
// https://blog.csdn.net/catoop/article/details/50180949
// java线程池继承关系：
// ThreadPoolExecutor extends AbstractExecutorService implements ExecutorService extends Executor
// 还有一个支持延时执行线程和可以重复执行线程的实现类：
// ScheduledThreadPoolExecutor extends ThreadPoolExecutor implements ScheduledExecutorService
// 大家把这些类中的相关方法弄清楚，使用线程池就不在话下了。其实弄清楚里面各个方法的功能也就够了。
// 最重要的还是在实践中总结经验，企业需要的是能实际解决问题的人。

/**
 * 测试类
 *
 * @author SHANHY(365384722@QQ.COM)
 * @date   2015年12月4日
 */
public class ExecutorTest {

    public static void main(String[] args) {

        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();

        for (int i = 0; i < 200; i++) {
            Future<?> future = pool.submit(new ExcuteTask1(i+""));
//          try {
//              如果接收线程返回值，future.get() 会阻塞，如果这样写就是一个线程一个线程执行。所以非特殊情况不建议使用接收返回值的。
//              System.out.println(future.get());
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
        }

        for (int i = 0; i < 200; i++) {
            pool.execute(new ExcuteTask2(i+""));
        }

        //关闭线程池，如果是需要长期运行的线程池，不用调用该方法。
        //监听程序退出的时候最好执行一下。
        pool.shutdown();
    }

    /**
     * 执行任务1，实现Callable方式
     *
     * @author SHANHY(365384722@QQ.COM)
     * @date   2015年12月4日
     */
    static class ExcuteTask1 implements Callable<String> {
        private String taskName;

        public ExcuteTask1(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String call() throws Exception {
            try {
//              Java 6/7最佳的休眠方法为TimeUnit.MILLISECONDS.sleep(100);
//              最好不要用 Thread.sleep(100);
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));// 1000毫秒以内的随机数，模拟业务逻辑处理
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-------------这里执行业务逻辑，Callable TaskName = " + taskName + "-------------");
            return ">>>>>>>>>>>>>线程返回值，Callable TaskName = " + taskName + "<<<<<<<<<<<<<<";
        }
    }

    /**
     * 执行任务2，实现Runable方式
     *
     * @author SHANHY(365384722@QQ.COM)
     * @date   2015年12月4日
     */
    static class ExcuteTask2 implements Runnable {
        private String taskName;

        public ExcuteTask2(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));// 1000毫秒以内的随机数，模拟业务逻辑处理
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("-------------这里执行业务逻辑，Runnable TaskName = " + taskName + "-------------");
        }

    }

}
