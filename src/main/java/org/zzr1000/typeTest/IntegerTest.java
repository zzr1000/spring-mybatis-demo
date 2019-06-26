package org.zzr1000.typeTest;

import org.junit.Test;

public class IntegerTest {


    @Test
    public void test(){
        System.out.println(Integer.valueOf("444",16));//1092
        System.out.println(Integer.valueOf("a",16));//10
        System.out.println(Integer.valueOf("b",16));//11
        System.out.println(Integer.valueOf("f808",16));//63496
      //System.out.println(Integer.valueOf("9ca9f808",16));//报错
    }

}
