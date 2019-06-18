package org.zzr1000.guiceTest.a;

import com.google.inject.Singleton;

@Singleton//声明此DaoImpl为单例
public class DaoImpl implements Dao{

    @Override
    public void testGuice() {
        System.out.println("测试guice，实现依赖!");
    }

}