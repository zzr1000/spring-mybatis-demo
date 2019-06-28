package org.zzr1000.typeTest;

import org.junit.Test;
/*
如果出现乱码，可以采用这种方式：
https://www.cnblogs.com/fengwenzhee/p/7239822.html
核心就是：对char直接进行(int)强制类型转换，就可以获得对应的ascii码
string转ascii码，也是string中的单个char，挨个转ascii码：.

ascii码转int的核心就是：(char) Integer.parseInt(1)或者直接(char)1 转换

 */
public class AsciiToStringTest {

    public static String stringToAscii(String value)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1)
            {
                sbu.append((int)chars[i]).append(",");
            }
            else {
                sbu.append((int)chars[i]);
            }
        }
        return sbu.toString();
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    public static void main(String[] args) {

        String str = "{name:1234,password:4444}";
        String asciiResult = stringToAscii(str);
        System.out.println(asciiResult);
        String stringResult = asciiToString(asciiResult);
        System.out.println(stringResult);
    }


}
