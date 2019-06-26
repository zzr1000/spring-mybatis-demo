package org.zzr1000.typeTest;

import org.junit.Test;

public class IntToString {

    @Test
    public void test(){

        System.out.println(String.valueOf(1));

        //字节对比：
        System.out.println(IntToByte(1).length);// 4:
        System.out.println("1".getBytes().length);// 1:
        System.out.println(String.valueOf(1).getBytes().length);// 1:

    }



    /**
     * int转byte数组
     * @param num
     * @return
     */
    public static byte[] IntToByte(int num){
        byte[]bytes=new byte[4];
        bytes[0]=(byte) ((num>>24)&0xff);
        bytes[1]=(byte) ((num>>16)&0xff);
        bytes[2]=(byte) ((num>>8)&0xff);
        bytes[3]=(byte) (num&0xff);
        return bytes;
    }

}
