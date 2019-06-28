package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.*;

/*
字符流：Read、Write
字节流：InputStream、OutputStream：

注意点：
1、BufferedReader：适合读取一行
2、InputStreamReader是连接字节操作、字符操作的桥梁类
 */
public class ReadWriteTest {

    @Test
    public void test() throws IOException {
        FileReader fr = new FileReader(new File("README.md"));
        BufferedReader br = new BufferedReader(fr);//适合一行一行读
        String s = "";
        while ((s = br.readLine())!=null){
            System.out.println(s);
        }
        fr.close();
    }

    @Test
    public void test2() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("README.md")));
        String s = "";
        while ((s = br.readLine())!=null){
            System.out.println(s);
        }
        br.close();
    }

    @Test
    public void test3() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("tt"));

        bw.append("xxx");
        bw.newLine();
        bw.append("yyy");
        bw.newLine();
        bw.append("zzz");

        bw.flush();
        bw.close();

    }

}
