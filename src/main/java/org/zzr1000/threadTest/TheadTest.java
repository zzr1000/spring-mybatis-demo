package org.zzr1000.threadTest;

public class TheadTest {

    public static void main(String[] args) {
        new MyThread("t1").start();
        new MyThread("t2").start();
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
        }
    }
}