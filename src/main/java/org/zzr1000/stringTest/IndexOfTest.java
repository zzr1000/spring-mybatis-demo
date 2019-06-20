package org.zzr1000.stringTest;

import org.junit.Test;

public class IndexOfTest {

    @Test
    public void test(){
        System.out.println("ab1bcdefg.hijk".indexOf("b"));//1
        System.out.println("ab1bcdefg.hijk".indexOf("b1bc"));//1
        System.out.println("ab1bcdefg.hijk".indexOf(98));//1//98：b的ascii码

        System.out.println("ab1bcdefg.hijk".indexOf("b",2));//3
    }

}
