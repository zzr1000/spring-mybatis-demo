package org.zzr1000.stringTest;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
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


    //getByte:得到一个操作系统默认的编码格式的字节数组
    @Test
    public void getByteTest() throws UnsupportedEncodingException {
        String a = "你";
        byte[] array = a.getBytes();
        byte[] array1 = a.getBytes("UTF-8");//字节数组:-128~127之间的数组成的数组:字节数组
        byte[] array2 = a.getBytes("GBK");
        byte[] array3 = a.getBytes("ISO8859-1");
        System.out.println("default:");
        for(byte x:array1){
            System.out.println(x);
        }
        System.out.println("UTF-8:");
        for(byte x:array1){
            System.out.println(x);
        }
        System.out.println("GBK:");
        for(byte x:array2){
            System.out.println(x);
        }
        System.out.println("ISO8859-1:");
        for(byte x:array2){
            System.out.println(x);
        }

        //得到系统编码格式
        System.out.println(System.getProperty("file.encoding"));

    }

    //有字节数组得到字符(字符串)
    @Test
    public void newString() throws UnsupportedEncodingException {
        System.out.println(new String("中".getBytes()));//取按默认的取,转按默认的转
        System.out.println(new String("中".getBytes("GBK"),"GBK"));//指定取、转编码格式
        System.out.println(new String("中".getBytes("UTF-8"),"UTF-8"));
        System.out.println(new String("中".getBytes("ISO8859-1"),"ISO8859-1"));
        System.out.println(new String("中".getBytes("GBK"),"UTF-8"));//有问题
    }


}
