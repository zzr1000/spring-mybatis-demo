package org.zzr1000.designPatternTest;

/*
单例模式的类只有一个实例，并提供一个访问这个实例的全局访问点。

应用场景
1.有些场景需要单例才好实现，如计数器
2.创建一个对象需要过多资源，如IO、数据库连接等
 */

public class SingletonTest{}

//饿汉式：初始化静态变量时instance创建一次。
//缺点：没有懒汉式的延迟初始化效果，可能造成资源浪费。
class SingletonTest1 {

    private static SingletonTest1 instance = new SingletonTest1();
    private SingletonTest1(){}
    public static SingletonTest1 getInstance(){
        return instance;
    }
}

/*
懒汉式-同步锁:加上同步锁，在多线程下可以使用。
缺点：每次通过getInstance方法得到singleton实例的时候都有一个试图去获取同步锁的过程，耗时。
 */
class SingletonTest2{
    private static SingletonTest2 instance = null;
    private SingletonTest2(){}
    public static synchronized SingletonTest2 getInstance(){
        if(instance == null){
            instance = new SingletonTest2();
        }
        return instance;
    }
}

/*
懒汉式-双重检查:只有当instance为null时，需要获取同步锁并创建实例。
 */
class SingletonTest3 {
    private static SingletonTest3 instance = null;
    private SingletonTest3() {}
    public static SingletonTest3 getInstance() {
        if (instance == null) {
            synchronized (SingletonTest3.class) {
                if (instance == null) {
                    instance = new SingletonTest3();
                }
            }
        }
        return instance;
    }
}

/*
静态内部类式:定义一个静态内部类，在第一次用这个内部类时会创建一个实例。
 */
class SingletonTest4{
    private SingletonTest4(){}

    private static class SingletonTest4Holder{
        private static final SingletonTest4 instance = new SingletonTest4();
    }
    public static SingletonTest4 getInstance() {
        return SingletonTest4Holder.instance;
    }
}

/*
枚举式:每个枚举实例都是static final的，所以只能实例化一次。
(和静态内部类原理类似：static final只能实例化一次)
访问枚举实例时执行私有构造函数会实例化我们的单例对象。防止反射和反序列化漏洞！
 */
class SingletonTest5{
}
enum SingletonTest5ENUM{
    INSTANCE;
    private SingletonTest5 instance;
    SingletonTest5ENUM(){instance = new SingletonTest5();}
    public SingletonTest5 getInstance(){
        return instance;
    }
}