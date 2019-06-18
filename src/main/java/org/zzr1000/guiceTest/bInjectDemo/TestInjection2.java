package org.zzr1000.guiceTest.bInjectDemo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.zzr1000.guiceTest.aBaseDemo.Dao;

//构造方法注入
@Singleton
public class TestInjection2 {

    private Dao dao;

    @Inject
    public TestInjection2(Dao dao) {
        this.dao = dao;
    }

    void test() {
        dao.testGuice();
        System.out.println("TestInjection2");
    }
}
