package org.zzr1000.threadTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
Callable 实现步骤:
1.创建Callable的实现类，并实现call()方法，然后创建这个实现类的实例。
这里我们不写实现类，而是采用更方便的Lambda表达式。
2.使用FutureTask类包装Callable对象。
3.使用FutureTask对象作为Thread对象的target创建Thread线程对象并调用start()方法启动线程。
4.调用FutureTask对象的get()方法获得子线程执行结束后的返回结果。
 */
public class CallableTest {
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(
                ()->{
                    System.out.println("xxxxx");
                    return 1;
                });
        new Thread(futureTask).start();

        try {
            System.out.println("callable return result:" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
