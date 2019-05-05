package org.zzr1000.threadTest;

public class TheadTest {

    public static void main(String[] args) {
        System.out.println("Begin");
        new MyThread("t1").start();
        new MyThread("t2").start();
        System.out.println("End");
    }
}


class MyThread extends Thread{

    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    MyThread(String tname){
        this.tname = tname;
    }

    public void run(){
        for(int i = 0 ; i < 20 ; i+=1){
            System.out.println(tname + ":" + i);
            yield();//让步/放弃
        }
    }
}