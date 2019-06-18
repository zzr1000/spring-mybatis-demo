package org.zzr1000.guiceTest.a;

import com.google.inject.AbstractModule;

public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        //这句代码的意思是说：运行时动态的将DaoImpl对象赋给Dao对象并且是单例的
        bind(Dao.class).to(DaoImpl.class);
    }

}