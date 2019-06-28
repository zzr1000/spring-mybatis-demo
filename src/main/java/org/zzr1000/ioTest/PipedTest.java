package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/*

在java中，PipedOutputStream和PipedInputStream分别是管道输出流和管道输入流。
它们的作用是让多线程可以通过管道进行线程间的通讯。
在使用管道通信时，必须将PipedOutputStream和PipedInputStream配套使用。
使用管道通信时，大致的流程是：我们在线程A中向PipedOutputStream中写入数据，
这些数据会自动的发送到与PipedOutputStream对应的PipedInputStream中，
进而存储在PipedInputStream的缓冲中；
此时，线程B通过读取PipedInputStream中的数据。
就可以实现，线程A和线程B的通信。

    write data to pipe           read data from pipe
pos----------------------->pipe----------------------->pis

*/
//refer to:
//https://www.cnblogs.com/skywang12345/p/io_04.html
public class PipedTest {

    @Test
    public void test() throws Exception {

        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        pis.connect(pos);

        new Thread(()-> {
            try {
                produce(pos);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    public static void produce(PipedOutputStream pos) throws IOException, InterruptedException {
        for(int i = 1; i <= 50; i++){
            pos.write(i);
            pos.flush();
            System.out.println("Writing:"+i);
            Thread.sleep(1000);
        }
    }

    public static void consume(PipedInputStream pis) throws IOException {
        int num = -1;
        while ((num = pis.read()) != -1){
            System.out.println("Reading:" + num);
        }
        pis.close();
    }

}
