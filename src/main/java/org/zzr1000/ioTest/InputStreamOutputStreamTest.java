package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
InputStream、OutputStream：针对字节的操作：
    子类：FileInputStream、FileOutputStream
Read、Write：针对文本的操作
 */
public class InputStreamOutputStreamTest {

    @Test
    public void test() throws IOException {

        FileInputStream fis = new FileInputStream("README.md");
        int data = fis.read();
        byte byteData;
        while (data != -1){
            System.out.println("data:"+data);//输出的是：单个字节对应的ascii码的十进制数值：因为data是int，直接进行了：类型转型
            byteData=(byte)data;
            System.out.println((char) byteData);
            data=fis.read();
        }
        fis.close();
    }

    @Test//不进行类型转换：
    public void test1() throws IOException {

        FileInputStream fis = new FileInputStream("README.md");

        for(int i = 1 ; i <= 10 ; i++){
            int t = fis.read();
            System.out.println(t);//也是直接转成了单个字符对应的ascii码十进制数值：.
            System.out.println((byte)t);//也是byte对应的int类型：字节数组的十进制表示
            System.out.println(Integer.toBinaryString(t));//int转换为二进制：字节数组的十进制表示
            System.out.println((char) t);//int类型，可以直接转换为char类型：int对应的ascii码
        }

    }
}
