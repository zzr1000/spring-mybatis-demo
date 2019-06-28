package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/*
字符流：Read、Write
字节流：InputStream、OutputStream
 */
public class ReadWriteTest {

    @Test
    public void test() throws FileNotFoundException {
        FileReader fr = new FileReader(new File(""));
    }

}
