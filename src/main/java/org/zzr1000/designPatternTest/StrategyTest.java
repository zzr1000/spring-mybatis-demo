package org.zzr1000.designPatternTest;


/*
refer to:
https://www.toutiao.com/i6646541945495618056/

一.简单介绍
策略模式是指有一定行动内容的相对稳定的策略名称。
定义一系列的算法，把它们一个个封装起来, 并且使它们可相互替换。

二.应用场景
1.多个类只区别在表现行为不同，可以使用Strategy模式，在运行时动态选择具体要执行的行为。
2.需要在不同情况下使用不同的策略（算法），或者策略还可能在未来用其它方式来实现。
3.对客户隐藏具体策略（算法）的实现细节，彼此完全独立。

三.角色组成
1.抽象策略角色
策略类，通常由一个接口或者抽象类实现。

2.具体策略角色
包装了相关的算法和行为。

3.环境角色
持有一个策略类的引用，最终给客户端调用。

 */
public class StrategyTest {

    public static void main(String[] args) {
        PersonContext pc = new PersonContext(new AirplanStrategy());
        pc.travel();

        PersonContext pc2 = new PersonContext(new TrainStrategy());
        pc2.travel();
    }

}

/*
抽象策略角色
 */

interface TravelStrategy{
    void travel();
}

/*
具体策略角色
 */
class AirplanStrategy implements TravelStrategy{
    @Override
    public void travel() {
        System.out.println("Airplan travel ...");
    }
}


/*
具体策略角色
 */
class TrainStrategy implements TravelStrategy{
    @Override
    public void travel() {
        System.out.println("Train travel ...");
    }
}

/*
环境角色
 */
class PersonContext{
    private TravelStrategy travelStrategy;
    public PersonContext(TravelStrategy travelStrategy){
        this.travelStrategy = travelStrategy;
    }
    public void travel(){
        this.travelStrategy.travel();
    }
}











