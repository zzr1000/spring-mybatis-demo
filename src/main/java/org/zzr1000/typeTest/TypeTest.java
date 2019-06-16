package org.zzr1000.typeTest;

public class TypeTest {

    /* int：4个字节、32位：4个Byte、32个bit
     * 原码：最高位符号位：剩余部分表示真值:
     * 反码：原码基础上，正数反码就是他本身，负数除符号位之外全部按位取反
     * 补码：正数的补码就是自己本身，负数的补码是在自身反码的基础上加1.
     * Java中用补码表示二进制数，补码的最高位是符号位，最高位为“0”表示正数，最高位为“1”表示负数。
     * 正数补码为其本身；负数补码为其::::::::::绝对值各位取反加1::::::::::
     * Byte类型：8个字节：
     * 最大正数：01111111：127
     * 最小正数：00000001：1
     * 最大负数：11111111：-1
     * 最小负数：10000000：-128(按理解是负0)
     * 一种解释：
     * 正数：符号位0:00000001~01111111:1~127
     * 0  ：00000000
     * 负数：符号位1：10000001~11111111：减1：得到绝对值的取反：10000000~11111110：
     * 另一种解释：byte占一个字节空间，最高位是符号位，
     * 剩余7位能表示0-127，加上符号位的正负，就是-127至+127，
     * 但负0没必要，为充分利用，就用负零表示-128（即原码1000，0000）（计算机转补码后存储）
     * 另一种解释：
     * 直接给总结：计算机规定了0000 0000 代表0，
     * 1000 0000代表的-0没有意义，必须找个~127~127之外的数和它对应，
     * 「人为规定-0就是-128」，而且这么做完美适合计算机做减法运算。
     * 参考：https://blog.csdn.net/sheng_Mu555/article/details/78949700
     */


    public static void main(String[] args) {
      //System.out.println(IntToByte(1));
        byte[] b = IntToByte(1);
        for(byte a:b){
            System.out.println(a);
        }

        System.out.println(Byte2Int(IntToByte(1)));
    }

    /**
     * byte数组转int类型的对象
     * @param bytes
     * @return
     */
    public static int Byte2Int(byte[]bytes) {
        return (bytes[0]&0xff)<<24
                | (bytes[1]&0xff)<<16
                | (bytes[2]&0xff)<<8
                | (bytes[3]&0xff);
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
