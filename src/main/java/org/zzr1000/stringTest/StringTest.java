package org.zzr1000.stringTest;

import org.junit.Test;

import java.math.BigInteger;

public class StringTest {

    public static void main(String[] args) {

    }

    @Test
    public void fBigIntegerTest(){
        System.out.println(new BigInteger("1111"));//默认10进制
        System.out.println(new BigInteger("f",16));//radix:进制
    }

    @Test
    public void fStringFormat(){
        System.out.println(String.format("%016x", 200));//%x：转换为16进制;016:添0补齐16位//重要
        System.out.println(String.format("%016x", 200).getBytes());
    }

    //-128~127：//byte:字节:8个bit：1个大B(byte)是8个小b(bit)
    //byte类型的数据是8位带符号的二进制数
    //在计算机中，8位带符号二进制数的取值范围是[-128, 127]，
    //所以在Java中，byte类型的取值范围也是[-128, 127]。
    //二进制从 00000000 到01111111到10000000到 11111111
    //即 十进制从 0 到 127 到 -128 到 -1
    @Test
    public void byteTest(){
        byte a = 127;
        a+=1;
        System.out.println(a);//-128

        int b = 456;//111001000
        byte test = (byte) b;//111001000取8位:11001000(负数的补码):对应的原码(00111000):56
        System.out.println(test);//-56
    }

}
