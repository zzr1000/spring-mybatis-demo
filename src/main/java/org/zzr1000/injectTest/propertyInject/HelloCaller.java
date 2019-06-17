package org.zzr1000.injectTest.propertyInject;

import com.google.inject.Inject;

//HelloCaller将会去调用Hello这个接口提供的服务
public class HelloCaller {
    //通过@Inject，来完成属性的注入
    @Inject
    private Hello hello ;    //这个就相当于：private Hello hello = new HelloImpl(); 把Hello实例化了
    //调用Hello的sayHello方法（实际上就是去调用HelloImpl的sayHello，因为我们将Hello的实现指定是HelloImpl）
    public void sayHello(){
        hello.sayHello() ;
    }
}
