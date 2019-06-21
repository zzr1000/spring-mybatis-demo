package org.zzr1000.classLoaderTest;


import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//URLClassLoader 获取外部的jar文件以及class文件
//refer to : https://blog.csdn.net/qq_29956725/article/details/86677379
//测试jar包产生：jar cvf test.jar Test.class，放在项目根目录下：.. : 注意包名信息：..
public class URLClassLoaderTest {

    //这样写，拿不到jar包，拿不到类：..
    //private static String pathJar = "./test.jar";
    private static String jarName = "test.jar";

    //区别：不需要加具体class文件
    private static String pathClass = "./";


    public static void main(String[] args) throws ClassNotFoundException,
            MalformedURLException,
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {
        String p1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File f1 = new File(p1);
        String s1 = new File(f1.getParent()).getParent();
        String pathJar = s1 + "\\" + jarName;
        System.out.println(pathJar);
        URL url = new URL("file:" + pathJar);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url }); // 加载外部的jar包
        //类名如果有完整的包名，那么jar文件中，也需要有完整的对应目录信息，否则会报找不到对应类
        Class<?> clazz = urlClassLoader.loadClass("org.zzr1000.classLoaderTest.Test");

        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("say", null);
        method.invoke(instance);//Hello
    }
}
