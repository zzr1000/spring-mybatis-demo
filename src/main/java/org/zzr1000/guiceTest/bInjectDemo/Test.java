package org.zzr1000.guiceTest.bInjectDemo;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Test {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TestModule());
        TestInjection1 test1 = injector.getInstance(TestInjection1.class);
        test1.test();

        TestInjection2 test2 = injector.getInstance(TestInjection2.class);
        test2.test();

        TestInjection3 test3 = injector.getInstance(TestInjection3.class);
        test3.test();
    }
}
