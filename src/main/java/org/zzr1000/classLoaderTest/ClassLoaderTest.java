package org.zzr1000.classLoaderTest;

public class ClassLoaderTest {

    public static void main(String[] args) {
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent());
        System.out.println(ClassLoaderTest.class.getClassLoader().getParent().getParent());

        //AppClassLoader又叫系统类加载器
        System.out.println(ClassLoader.getSystemClassLoader());//AppClassLoader:sun.misc.Launcher$AppClassLoader@18b4aac2
    }
}
