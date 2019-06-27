package org.zzr1000.java8Test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/*
try-with-resources的用法就是，
在try关键字的后面跟一个括号，把需要关闭的资源定义在括号内。
在try块执行完之后会自动的释放掉资源。


并不是所有资源都可以被try-with-resources自动关闭的，
只有实现了java.lang.AutoCloseable接口的类，才可以被自动关闭。
如果没有实现java.lang.AutoCloseable的类定义在try的括号内，则会在编译器就报错。

try-with-resources可以使代码更加简洁而且不容易出错。
相比传统的try-catch-finally的写法，显然try-with-resources优点更多，
至少不会存在finally关闭资源因为没判空而导致空指针的问题。
 */
public class TryWithResourceTest {

    @Test
    public void test5() {
        try (InputStream inputStream = new FileInputStream("D:\\head.jpg")) {
//            byte[] bytes = inputStream.read();
            // do something
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
