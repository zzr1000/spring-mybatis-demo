package org.zzr1000.injectTest.constructorInject;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Client {

    //创建一个测试程序
    public static void main(String[] args) {

        //Module[] 数组还可以作为io.airlift.bootstrap.Bootstrap的参数
        Injector in = Guice.createInjector(new Module(){
            @Override
            public void configure(Binder arg0) {
                //null
            }
        }) ;
        //得到HelloCaller的实例
        HelloCaller helloCaller = in.getInstance(HelloCaller.class) ;
        //调用sayHello方法
        helloCaller.sayHello() ;

        System.out.println("==============");

        Injector in2 = Guice.createInjector();
        HelloCaller helloCaller2 = in2.getInstance(HelloCaller.class) ;
        helloCaller2.sayHello();

    }
}
