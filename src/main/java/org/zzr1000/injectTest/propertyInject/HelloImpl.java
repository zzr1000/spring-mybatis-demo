package org.zzr1000.injectTest.propertyInject;


import com.google.inject.ImplementedBy;

//这里没有加@Singleton，表示是动态创建这个类，不是单例的
public class HelloImpl implements Hello{
    @Override
    public void sayHello() {
        System.out.println("HelloImpl Say Hello");
    }
}

@ImplementedBy(HelloImpl.class)//通过@ImplementedBy注解，将接口和实现类绑定在一起
interface Hello {
    void sayHello();
}