package org.zzr1000.guiceTest.b;

import com.google.inject.AbstractModule;
import org.zzr1000.guiceTest.a.Dao;
import org.zzr1000.guiceTest.a.DaoImpl;

//module中需要绑定对应TestInjection这个类才可以使用.
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dao.class).to(DaoImpl.class);
        bind(TestInjection1.class);// 和a包中的区别：调用到的类，都需要bind进来：presto connector写法
    }
}
