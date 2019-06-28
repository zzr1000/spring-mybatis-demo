package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
InputStream、OutputStream：针对字节的操作：
    子类：FileInputStream、FileOutputStream
Read、Write：针对文本的操作
 */
public class FileInputOutputStreamTest {

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


    //上述写法可以简写如下：int、char、byte之间可以相互转换，所以，不需要这么多中间转换
    @Test
    public void test2() throws IOException {

        FileInputStream fis = new FileInputStream("README.md");
        int data = fis.read();
        while (data != -1){
            System.out.print((char) data);
            data=fis.read();
        }
        fis.close();
    }

    //更简化写法：
    @Test
    public void test3() throws IOException {
        FileInputStream fis = new FileInputStream("README.md");
        int data ;
        while ((data = fis.read())!= -1){
            System.out.print((char) data);
        }
        fis.close();
    }

    //try resource写法：
    @Test
    public void test4() throws IOException {
        try(FileInputStream fis = new FileInputStream("README.md")){
            int data ;
            while ((data = fis.read())!= -1){
                System.out.print((char) data);
            }
        }catch (Exception e){//如果不想输出异常信息，也可以不需要catch部分：..
            e.printStackTrace();
        }
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


    @Test
    public void test6(){
        try(FileInputStream fis = new FileInputStream("");
            FileOutputStream fos = new FileOutputStream("")){
            byte[] data = new byte[2048];
            int length = 0;
            while ((length = fis.read(data,0,data.length))!=-1){
                fos.write(data,0,length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
