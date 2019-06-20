package org.zzr1000.classLoaderTest;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;


//测这个功能过程中，需要把Test.java文件，在编译后，改名
//不然会知己引用到这个java文件，不能准确测试功能正确性
public class CustomClassLoader extends ClassLoader {

    private final String classesDir;
    public CustomClassLoader(String classesDir) {
        this.classesDir = classesDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = name;
        if (fileName.indexOf('.') != -1) {
            //fileName = fileName.replaceAll("\\.", "\\\\");
            fileName = fileName.split("\\.")[fileName.split("\\.").length - 1];
        }
        fileName = fileName + ".class";
        try {
            try (FileInputStream in = new FileInputStream(classesDir + fileName)) {
                try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer,0,len);
                    }
                    byte[] data = out.toByteArray();
                    return defineClass(name, data, 0, data.length);
                }
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(name);
        }
    }
    public static void main(String[] args) throws ReflectiveOperationException{

        //1. 将Test.java 编译为Test.class 后复制到 E:\classes 下，当然也可以选择其他目录作为加载目录。

        //2. 加载//"./"：指的是整个工程的目录位置：当不知道的时候，可以输入一个文件到"./"确认位置指向:.
        ClassLoader classLoader = new CustomClassLoader("./");
        Class<?> clazz = classLoader.loadClass("org.zzr1000.classLoaderTest.Test");//如果你的Test在一个包内，需要加上包名，如x.y.z.Test


        //3. 通过反射调用say()方法
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("say", null);
        method.invoke(instance);//Hello
    }
}
