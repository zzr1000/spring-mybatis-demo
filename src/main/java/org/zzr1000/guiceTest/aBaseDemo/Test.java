package org.zzr1000.guiceTest.aBaseDemo;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Test {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new TestModule());
        Dao dao = injector.getInstance(DaoImpl.class);
        dao.testGuice();
    }
}
