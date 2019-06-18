package org.zzr1000.guiceTest.bInjectDemo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.zzr1000.guiceTest.aBaseDemo.Dao;

//setter方法注入
@Singleton
public class TestInjection3 {

    private Dao dao;

    @Inject
    public void setDao(Dao dao) {
        this.dao = dao;
    }

    void test() {
        dao.testGuice();
        System.out.println("TestInjection3");
    }

}
