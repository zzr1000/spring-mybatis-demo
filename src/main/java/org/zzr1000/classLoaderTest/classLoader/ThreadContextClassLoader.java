package org.zzr1000.classLoaderTest.classLoader;

import java.io.Closeable;
import java.io.IOException;

public class ThreadContextClassLoader implements Closeable {


    private final ClassLoader originalThreadContextClassLoader = Thread.currentThread().getContextClassLoader();

    //方法的作用是：设置新的ThreadContextClassLoader
    //最后在关闭的时候，在把原有的ThreadContextClassLoader设置还原回去
    public ThreadContextClassLoader(ClassLoader newThreadContextClassLoader) {
        Thread.currentThread().setContextClassLoader(newThreadContextClassLoader);
    }

    @Override
    public void close() throws IOException {
        Thread.currentThread().setContextClassLoader(this.originalThreadContextClassLoader);
    }
}
