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

        //如果有多个实现类，会有多个、多次调用：从这个地方可以看出：
        //presto放plugin jar文件的目录名称可以任意取，
        //另外甚至可以多个plugin的jar文件放在一个目录下，也是可以的 :..
        for (Sing sing : loader) {
            sing.sing();
        }
    }
}
