package org.zzr1000.classLoaderTest.classLoader;

import org.junit.Test;

import java.io.File;

//refer to : https://blog.csdn.net/z69183787/article/details/22774537
public class ClassPathTest {

    @Test
    public void test(){
        System.out.println(">>>>>>1::: " + System.getProperty("java.class.path"));
        System.out.println(">>>>>>2::: " + System.getProperty("sun.boot.class.path"));
        //项目根目录下：null：说明不是指向这个路径
        System.out.println(">>>>>>3::: " + Thread.currentThread().getContextClassLoader().getResource("README.md"));
        //项目编译之后的class目录：输出file:/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/tb.sql
        //说明该方法读取的是target/classes目录下的文件
        System.out.println(">>>>>>4::: " + Thread.currentThread().getContextClassLoader().getResource("tb.sql"));
        //输出：java.io.BufferedInputStream@343f4d3d
        System.out.println(">>>>>>5::: " + Thread.currentThread().getContextClassLoader().getResourceAsStream("tb.sql"));
        //得到的是当天classpath绝对路径：输出：file:/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/
        //但是有一个问题：
        //test-class存在的时候，是会显示file:/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/
        //实际项目打包过程中，是不存在这种情况的：所以，这个显示问题不大
        //推荐使用：Thread.currentThread().getContextClassLoader().getResource("")获取路径及配置文件等:.
        System.out.println(">>>>>>6::: " + ClassPathTest.class.getResource("/"));
        System.out.println(">>>>>>7::: " + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println(">>>>>>8::: " + ClassLoader.getSystemResource(""));
        System.out.println(">>>>>>9::: " + ClassLoader.getSystemResource("tb.sql"));
        //得到的是当前类class文件所在目录：file:/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/org/zzr1000/classLoaderTest/
        System.out.println(">>>>>>a::: " + ClassPathTest.class.getResource(""));
        //得到如下：/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/
        System.out.println(">>>>>>b::: " + Thread.currentThread().getContextClassLoader().getResource("").getPath());
        String p1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //输出：D:\zzr\git\spring-mybatis-demo2\spring-mybatis-demo\target
        System.out.println(">>>>>>c::: " + new File(p1).getParent());
    }
}
