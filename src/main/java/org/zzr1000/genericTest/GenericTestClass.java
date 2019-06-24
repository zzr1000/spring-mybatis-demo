package org.zzr1000.genericTest;

//这个类在定义的时候，并不知道具体类型，但是在使用这个类的时候，需要制定具体类型：
//Object替换为T：
//使用泛型类：构造具体实例的时候，在传入具体类型：类型参数化：
//把类型作为一个参数/变量：具体使用的时候，在确定参数/变量的具体值：形参、实参：形式类型参数、实际类型参数：..
//类型参数：不能使用基本数据类型，要使用包装类：比如：不能适应int，要使用Integer
//可以有多个形式类型参数:...

//泛型的好处：类型安全；消除强制类型转换：..

//类型擦除：类型T，只在编译的时候，起作用，替换为不同类型：
//但是在程序执行的时候，这个T是没有作用的，会被类型擦除：..
class Wrapper<T> {

    private T o;

    public Wrapper(T o){
        this.o = o ;
    }

    public T getO() {
        return o;
    }

    public void setO(T o) {
        this.o = o;
    }
}

public class GenericTestClass{
    public static void main(String[] args) {
        Wrapper<String> w1 = new Wrapper<>("Hello");//java7之后，类型可以省略
        Wrapper<String> w2 = new Wrapper<String>("Hello");
    }
}
