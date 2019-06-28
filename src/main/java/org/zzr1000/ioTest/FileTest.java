package org.zzr1000.ioTest;


import org.junit.Test;

import java.io.File;

/*
IO：对象：文件、网络
File类：文件：.
 */
public class FileTest {

    @Test
    public void test(){
        File f = new File("D:\\aa");
        System.out.println(f.isFile());
        System.out.println(f.exists());
    }


    @Test
    public void test2(){

        //打印的是，当前系统的根目录：这个也是之前使用property时候，读取成功的路径：使用了IO类，先做了处理
        System.out.println(System.getProperty("user.dir"));
        File f1 = new File("README.md");

        System.out.println(f1.exists());

    }
}
