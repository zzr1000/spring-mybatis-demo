package org.zzr1000.threadTest;

/*
利用Thread传入一个Runnable函数式接口（lambda表达式）实现：多线程，比较简单，易实现
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
