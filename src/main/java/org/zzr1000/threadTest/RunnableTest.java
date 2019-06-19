package org.zzr1000.threadTest;

import static java.lang.Thread.yield;

public class RunnableTest {


    public static void main(String[] args) {

        //1.创建Runnable的接口实现类的实例
        ThreadDemo1 s1=new ThreadDemo1();
        ThreadDemo1 s2=new ThreadDemo1();

        //2.用Runnable的接口实现类的实例作为Thread的target，创建Thread对象
        Thread t1=new Thread(s1);
        Thread t2=new Thread(s2,"High");//创建Thread对象的同时可以为之命名

        //启动线程
        t1.start();
        t2.start();

    }
}

class ThreadDemo1 implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getName()+"===="+i);
            yield();
        }
    }
}