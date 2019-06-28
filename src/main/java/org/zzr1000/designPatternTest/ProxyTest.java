package org.zzr1000.designPatternTest;

/*
refer to :
https://www.toutiao.com/i6646556166325273091/

一.简单介绍
为其他对象提供一种代理以控制对这个对象的访问。
在某些情况下，一个对象不适合或者不能直接引用另一个对象，
而代理对象可以在客户端和目标对象之间起到中介的作用。

二.应用场景
1.当我们想要隐藏某个类时，可以为其提供代理类。
2.当一个类需要对不同的调用者提供不同的调用权限时，可以使用代理类来实现。
3.当我们要扩展某个类的某个功能时，可以使用代理模式，在代理类中进行简单扩展。

三.角色组成
1.抽象角色:
通过接口或抽象类声明真实角色实现的业务方法。

2.代理角色:
实现抽象角色，是真实角色的代理，通过真实角色的业务逻辑方法来实现抽象方法，并可以附加自己的操作。

3.真实角色:
实现抽象角色，定义真实角色所要实现的业务逻辑，供代理角色调用。
 */
public class ProxyTest {

    public static void main(String[] args) {
        ISinger singer = new Singer();
        ISinger proxy = new SingerProxy(singer);
        proxy.sing();
    }

}

/*
抽象角色
 */

interface ISinger{
    void sing();
}

/*
真实角色
 */
class Singer implements ISinger{

    @Override
    public void sing() {
        System.out.println("singing....");
    }
}

/*
代理角色
 */

class SingerProxy implements ISinger{

    private ISinger iSinger;

    public SingerProxy(ISinger iSinger){
        this.iSinger = iSinger;
    }

    @Override
    public void sing() {
        System.out.println("say hello ...");
        iSinger.sing();
        System.out.println("say thanks ...");
    }
}























