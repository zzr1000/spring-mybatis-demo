package org.zzr1000.classLoaderTest.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/*
 * refer to :https://www.jianshu.com/p/e80d02b11bbf
 */

public class JDBCTest {

/*输出如下内容：
class com.mysql.jdbc.Driver------sun.misc.Launcher$AppClassLoader@18b4aac2
class com.mysql.fabric.jdbc.FabricMySQLDriver------sun.misc.Launcher$AppClassLoader@18b4aac2
class org.apache.hive.jdbc.HiveDriver------sun.misc.Launcher$AppClassLoader@18b4aac2
class org.apache.derby.jdbc.AutoloadedDriver------sun.misc.Launcher$AppClassLoader@18b4aac2
null
由于双亲委派模型，父加载器是拿不到通过子加载器加载的类的。
这里就引出了一个问题，为什么通过Bootstrap ClassLoader加载进来的DriverManager，
可以拿到Application ClassLoader加载进来的com.mysql.jdbc.Driver？
这个现象，就是破坏了双亲委派模型。
因为DriverManager是通过Bootstrap ClassLoader加载进来的，
而com.mysql.jdbc.Driver是通过classpath的JAR包加载进来的。
要想通过DriverManager，必须破坏双亲委派模型才能拿到。

DriverManager是如何拿到com.mysql.jdbc.Driver类的:
通过Thread.currentThread().setContextClassLoader()，
将Application ClassLoader设置为线程上下文加载器。
在DriverManager类里通过Thread.currentThread().getContextClassLoader()
拿到Application ClassLoader进行加载。

SPI加载类有什么优点
SPI的这种加载方式，就只需要定义好接口，引入符合规范的jar包，就可以获取到实现该接口的类了。
有点类似于IOC，上层只需要指定一个配置文件路径，在初始化的时候去扫描所有符合的配置文件路径，
并解析其中的内容，再去加载对应的类，就可以拿到所有该接口的实现了。

核心概念：双亲委派、线程上下文加载器

presto的plugin就是采用这种方式实现：. .

*/
    public static void main(String[] args)
    {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements())
        {
            driver = drivers.nextElement();
            System.out.println(driver.getClass() + "------" + driver.getClass().getClassLoader());
        }
        System.out.println(DriverManager.class.getClassLoader());
    }

}
