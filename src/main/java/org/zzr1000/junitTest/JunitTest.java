package org.zzr1000.junitTest;


import org.junit.Test;

public class JunitTest {

    @Test //在idea中, 能否直接引用到这个注解，和maven中依赖的scope和类所在的文件夹属性有关
    public void test(){
        System.out.println("Hello world");
    }

}
