package org.zzr1000.injectTest.setterInject;

import com.google.inject.Inject;

public class HelloCaller {

    private Hello hello ;

    public Hello getHello() {
        return hello;
    }

    //通过setter方法来注入hello属性
    @Inject
    public void setHello(Hello hello) {
        this.hello = hello;
    }

    public void sayHello(){
        hello.sayHello() ;
    }
}
