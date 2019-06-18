package org.zzr1000.guiceTest.bInjectDemo;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.zzr1000.guiceTest.aBaseDemo.Dao;

//属性注入：用的比较多，不需要new，只需要注解，就可以直接引用实例
@Singleton
public class TestInjection1 {

    @Inject
    private Dao dao;

    void test(){
        dao.testGuice();
        System.out.println("TestInjection1");
    }

}
