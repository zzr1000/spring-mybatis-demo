package org.zzr1000.classLoaderTest.guiceTest.cModuleDemo;

public class DarkDog extends Dog{

    @Override
    void eat() {
        System.out.println("子类方法");
        super.eat();
    }

}
