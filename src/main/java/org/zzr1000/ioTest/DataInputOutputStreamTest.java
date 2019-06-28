package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.*;

/*
处理基本数据类型的IO
 */
public class DataInputOutputStreamTest {


    @Test
    public void test1() throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("f:\\a"));
        dos.write(1);
        dos.writeUTF("AAAA");
        dos.close();
    }

    @Test
    public void test2() throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream("f:\\a"));
        System.out.println(dis.readInt());
      //System.out.println(dis.readUTF());
        dis.close();
    }

}
