package org.zzr1000.injectTest.setterInject;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class Client {

    //创建一个测试程序
    public static void main(String[] args) {

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

    }

}
