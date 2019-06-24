package org.zzr1000.classLoaderTest.injectTest.constructorInject;

import com.google.inject.Inject;

public class HelloCaller {

    //包含一个Hello属性
    private Hello hello ;
    //@Inject注解写在构造方法上，通过构造方法的方式注入属性hello
    @Inject//构造函数使用Inject注解的类，就可以在其他类中(Client.java)通过Injector.getInstance(xx)直接得到
    //如果不加这个注解，Client在调用的时候，就报如下错误：
    //Could not find a suitable constructor
    //in org.zzr1000.injectTest.constructorInject.HelloCaller.
    //Classes must have either one (and only one) constructor annotated with @Inject
    //or a zero-argument constructor that is not private.
    //这就是构造方法加Inject注解的意义:..
    public HelloCaller(Hello hello){
        this.hello = hello ;
    }

    public void sayHello(){
        hello.sayHello() ;
    }

}
