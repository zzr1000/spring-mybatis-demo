package org.zzr1000.guiceTest.cModuleDemo;

import com.google.inject.Binder;
import com.google.inject.Module;

public class TestModule2 implements Module {

    @Override
    public void configure(Binder arg) {
        System.out.println("TestModule2");
        arg.bind(Dog.class).to(DarkDog.class);
    }

}
