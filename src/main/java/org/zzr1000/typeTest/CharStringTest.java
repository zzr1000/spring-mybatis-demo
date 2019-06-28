package org.zzr1000.typeTest;

import org.junit.Test;

public class CharStringTest {

    @Test
    public void test() {
        char test1 = 'a';
        String test2 = "a";

        //char和int之间可以相互转换
        System.out.println((int)test1);
        System.out.println(Integer.toBinaryString(test1));
        System.out.println(Integer.toBinaryString((int)test1));

        //String和int之间，不能相互转换
//        System.out.println((int)test2);
//        System.out.println(Integer.toBinaryString(test2));

        //String可以转换为char数组
        char[] c = test2.toCharArray();
        for (char c1:c){
            System.out.println(Integer.toBinaryString(c1));
        }

        //string可以转换为byte：byte、char、int可以相互转换
        byte[] b = test2.getBytes();
        for (byte b1:b){
            System.out.println(b1);
            System.out.println(Integer.toBinaryString(b1));
        }

    }

}
