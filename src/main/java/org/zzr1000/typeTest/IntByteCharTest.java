package org.zzr1000.typeTest;

import org.junit.Test;

/*
char、int、byte之间可以相互转换
 */
public class IntByteCharTest {

    @Test
    public void test(){
        char test = 'a';
        System.out.println(test);
        System.out.println((int)test);
        System.out.println((byte)test);
        System.out.println((byte)(int)test);
        System.out.println((int)(byte)test);
        System.out.println((char) (int)(byte)test);
    }
}
