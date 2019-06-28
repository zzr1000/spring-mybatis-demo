package org.zzr1000.designPatternTest;

/*
模板模式：

一.简单介绍
定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。
Template Method使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。

二.应用场景
1.程序主框架相同，细节不同的情况下，可以使用模板方法。
2.一次性实现一个算法的不变的部分，并将可变的行为留给子类来实现。
3.在多个子类中拥有相同的方法，而且逻辑相同时，可以将这些方法抽出来放到一个模板抽象类中。

三.角色组成
1.抽象模板(Abstract Template)角色：
定义了一个或多个抽象操作，以便让子类实现。这些抽象操作叫做基本操作，它们是一个顶级逻辑的组成步骤。
定义并实现了一个模板方法。这个模板方法一般是一个具体方法，它给出了一个顶级逻辑的骨架，而逻辑的组成步骤在相应的抽象操作中，推迟到子类实现。顶级逻辑也有可能调用一些具体方法。

    2.具体模板(Concrete Template)角色：
实现父类所定义的一个或多个抽象方法，它们是一个顶级逻辑的组成步骤。
每一个抽象模板角色都可以有任意多个具体模板角色与之对应，而每一个具体模板角色都可以给出这些抽象方法（也就是顶级逻辑的组成步骤）的不同实现，从而使得顶级逻辑的实现各不相同。
 */
public class ModelTest {
    /*
    输出如下结果：
    Hummer1 start========
    Hummer1 alarm========
    Hummer1 stop========
    Hummer2 start========
    Hummer2 stop========
     */
    public static void main(String[] args) {
        HummerModel hm = new Hummer1();
        hm.run();
        hm = new Hummer2();
        hm.run();
    }
}

abstract class HummerModel{

    //抽象操作：
    //以便让子类实现。这些抽象操作叫做基本操作，它们是一个顶级逻辑的组成步骤。
    protected abstract void start();
    protected abstract void alarm();
    protected abstract void stop();

    //钩子方法：默认喇叭是会响的
    protected boolean isAlarm(){
        return true;
    }

    //模板方法
    //这个模板方法一般是一个具体方法，它给出了一个顶级逻辑的骨架，
    //而逻辑的组成步骤在相应的抽象操作中，推迟到子类实现。
    //顶级逻辑也有可能调用一些具体方法。
    public final void run(){
        start();
        if(isAlarm()){
            alarm();
        }
        stop();
    }
}

/*
实现父类所定义的一个或多个抽象方法，它们是一个顶级逻辑的组成步骤。
每一个抽象模板角色都可以有任意多个具体模板角色与之对应，
而每一个具体模板角色都可以给出这些抽象方法（也就是顶级逻辑的组成步骤）的不同实现，
从而使得顶级逻辑的实现各不相同。
 */
class Hummer1 extends HummerModel{

    @Override
    protected void start() {
        System.out.println("Hummer1 start========");
    }

    @Override
    protected void alarm() {
        System.out.println("Hummer1 alarm========");
    }

    @Override
    protected void stop() {
        System.out.println("Hummer1 stop========");
    }
}


class Hummer2 extends HummerModel{

    @Override
    protected void start() {
        System.out.println("Hummer2 start========");
    }

    @Override
    protected void alarm() {
        System.out.println("Hummer2 alarm========");
    }

    @Override
    protected void stop() {
        System.out.println("Hummer2 stop========");
    }

    public boolean isAlarm(){
        return false;
    }

}