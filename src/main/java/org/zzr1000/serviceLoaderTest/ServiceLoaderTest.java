package org.zzr1000.serviceLoaderTest;


//refer to :
// https://blog.csdn.net/jianggujin/article/details/81030983
// https://blog.csdn.net/shi2huang/article/details/80308531
// https://www.jianshu.com/p/d816737bfc06

import org.junit.Test;

import java.util.ServiceLoader;

public class ServiceLoaderTest {

    @Test//presto的plugin采用了类似方法，加载Plug接口的实现子类
    public void spiTest() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceLoader<Sing> loader = ServiceLoader.load(Sing.class, classLoader);

        for (Sing sing : loader) {
            sing.sing();
        }
    }
}
