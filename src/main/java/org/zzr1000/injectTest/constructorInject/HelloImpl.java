package org.zzr1000.injectTest.constructorInject;

import com.google.inject.ImplementedBy;

//这里没有加@Singleton，表示是动态创建这个类，不是单例的
public class HelloImpl implements Hello{

    @Override
    public void sayHello() {
        System.out.println("HelloImpl Say Hello");
    }
}

//将这个接口和实现类HelloImpl绑定到一起
@ImplementedBy(HelloImpl.class)
interface Hello {
    void sayHello() ;
}