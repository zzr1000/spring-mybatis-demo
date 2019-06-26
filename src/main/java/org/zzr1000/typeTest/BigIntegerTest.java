package org.zzr1000.typeTest;

import org.junit.Test;

import java.math.BigInteger;


// refer to:
// https://blog.csdn.net/w00w12l/article/details/7290750
// https://www.cnblogs.com/numen-fan/p/6500914.html
// https://www.jianshu.com/p/8b89ab19db84
// https://blog.csdn.net/qq_34246546/article/details/82149399

/*
BigInteger abs()  返回大整数的绝对值
BigInteger add(BigInteger val) 返回两个大整数的和
BigInteger and(BigInteger val)  返回两个大整数的按位与的结果
BigInteger andNot(BigInteger val) 返回两个大整数与非的结果
BigInteger divide(BigInteger val)  返回两个大整数的商
double doubleValue()   返回大整数的double类型的值
float floatValue()   返回大整数的float类型的值
BigInteger gcd(BigInteger val)  返回大整数的最大公约数
int intValue() 返回大整数的整型值
long longValue() 返回大整数的long型值
BigInteger max(BigInteger val) 返回两个大整数的最大者
BigInteger min(BigInteger val) 返回两个大整数的最小者
BigInteger mod(BigInteger val) 用当前大整数对val求模
BigInteger multiply(BigInteger val) 返回两个大整数的积
BigInteger negate() 返回当前大整数的相反数
BigInteger not() 返回当前大整数的非
BigInteger or(BigInteger val) 返回两个大整数的按位或
BigInteger pow(int exponent) 返回当前大整数的exponent次方
BigInteger remainder(BigInteger val) 返回当前大整数除以val的余数
BigInteger leftShift(int n) 将当前大整数左移n位后返回
BigInteger rightShift(int n) 将当前大整数右移n位后返回
BigInteger subtract(BigInteger val)返回两个大整数相减的结果
byte[] toByteArray(BigInteger val)将大整数转换成二进制反码保存在byte数组中
String toString() 将当前大整数转换成十进制的字符串形式
BigInteger xor(BigInteger val) 返回两个大整数的异或
 */
public class BigIntegerTest {


    @Test
    public void test(){

        BigInteger b = BigInteger.valueOf(100);
        System.out.println(b);

        System.out.println(new BigInteger("100",10).toString(16));//进制转换
        System.out.println(new BigInteger("100",10).toString(2));//进制转换
        System.out.println(new BigInteger("100",16).toString(10));

    }
}
