package org.zzr1000.fileTest;

import org.junit.Test;

import java.io.File;

public class FileTest {

    @Test//获得父目录
    public void test(){

        String p1 = "/D:/zzr/git/spring-mybatis-demo2/spring-mybatis-demo/target/classes/";
        System.out.println(p1);
        File f1 = new File(p1);
        System.out.println(f1.getParent());
        System.out.println(new File(f1.getParent()).getParent());

    }
}
