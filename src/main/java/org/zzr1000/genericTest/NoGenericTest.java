package org.zzr1000.genericTest;

// 想实现一个类处理多个数据类型：在不使用泛型情况下：.
// 使用Object接受任意类型，有问题和bug存在:
// 为解决这种问题，java5 之后，引入泛型：类型参数化：类型是作为一个参数，在使用的时候，传进来的:实现使代码使用于多种数据类型..
// 可以实现在 类、接口、方法下：分表称之为：泛型类、泛型接口、泛型方法：. .
// 当创建参数化类型的时候，编译器会负责类型转换操作：..
public class NoGenericTest {

    public static void main(String[] args) {
        Wapper w1 = new Wapper("Hello");
        Wapper w2 = new Wapper(111111);
        Wapper w3 = new Wapper(new Integer(200));

        String o1 =(String) w1.getO();
        Integer o2 =(Integer) w1.getO();//不使用泛型的问题所在：..
    }
}

class Wapper{
    Object o;

    public Wapper(Object o){
        this.o = o ;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}