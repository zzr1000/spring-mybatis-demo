package org.zzr1000.threadTest;

/*
利用Thread传入一个Runnable函数式接口（lambda表达式）实现：多线程，比较简单，易实现
 */
/*
实现接口和继承Thread的区别：
继承Thread类就不能再继承其它类，而实现接口还可以继承其它类。
实现接口的方式多个线程可以共享同一target。
 */
public class TreadRunnableTest {

    public static void main(String[] args) {
        new Thread(()->
        {
            while (true){
                System.out.println("aaa");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->
        {
            while (true){
                System.out.println("bbb");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
