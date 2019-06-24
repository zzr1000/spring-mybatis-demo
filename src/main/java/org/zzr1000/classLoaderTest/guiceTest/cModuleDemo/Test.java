package org.zzr1000.classLoaderTest.guiceTest.cModuleDemo;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Test {

    public static void main(String[] args) {
        Injector injector1 = Guice.createInjector(new TestModule1());
        Dog dog1=injector1.getInstance(Dog.class);
        dog1.eat();

        Injector injector2 = Guice.createInjector(new TestModule2());
        Dog dog2=injector2.getInstance(Dog.class);
        dog2.eat();
    }
}
