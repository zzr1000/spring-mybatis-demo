package org.zzr1000.designPatternTest;

/*
简单工厂模式是由一个工厂对象决定创建出哪一种产品类的实例。
简单工厂模式是工厂模式家族中最简单实用的模式，可以理解为是不同工厂模式的一个特殊实现。

二.应用场景：
1.工厂类负责创建的对象比较少。
2.客户只知道传入工厂类的参数，对于如何创建对象（逻辑）不关心。

三.角色组成
1.工厂（Creator）角色
简单工厂模式的核心，它负责实现创建所有实例的内部逻辑。工厂类的创建产品类的方法可以被外界直接调用，创建所需的产品对象。

2.抽象产品（Product）角色
简单工厂模式所创建的所有对象的父类，它负责描述所有实例所共有的公共接口。

3.具体产品（Concrete Product）角色
是简单工厂模式的创建目标，所有创建的对象都是充当这个角色的某个具体类的实例。
 */
public class FactoryTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Human man = SimpleFactory.makeHuman("man");
        man.say();
        Human woman = SimpleFactory.makeHuman("woman");
        woman.say();

        Human man2 = SimpleFactory2.makeHuman(Man.class);
        man2.say();
        Human woman2 = SimpleFactory2.makeHuman(Woman.class);
        woman2.say();

    }
}

/*
抽象产品角色-Human
 */
interface Human{
    void say();
}

/*
具体的产品角色：
 */
class Man implements Human{

    @Override
    public void say() {
        System.out.println("Man saying ....");
    }
}
class Woman implements Human{

    @Override
    public void say() {
        System.out.println("Woman saying ....");
    }
}

/*
工厂角色
 */

class SimpleFactory{
    public static Human makeHuman(String type){
        Human human;
        switch (type){
            case "man":
                human = new Man();
                break;
            case "woman":
                human = new Woman();
                break;
            default:
                human= new Man();
                break;
        }
        return human;
    }
}

/*
工厂角色：反射实现
 */
class SimpleFactory2{
    public static Human makeHuman(Class c) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (Human)Class.forName(c.getName()).newInstance();
    }
}


