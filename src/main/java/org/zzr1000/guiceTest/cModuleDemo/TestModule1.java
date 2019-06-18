package org.zzr1000.guiceTest.cModuleDemo;

import com.google.inject.AbstractModule;

public class TestModule1 extends AbstractModule {

    @Override
    protected void configure() {
        System.out.println("TestModule1");
        bind(Dog.class).to(DarkDog.class);
    }

}
