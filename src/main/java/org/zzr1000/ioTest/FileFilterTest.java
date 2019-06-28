package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.File;

public class FileFilterTest {

    @Test
    public void test(){

        File d = new File("src\\main\\java\\org\\zzr1000\\ioTest");
        System.out.println(d.exists());

        for(String f1 : d.list()){
            System.out.println(f1);
        }

        for(File f2 : d.listFiles()){
            System.out.println(f2);
        }

        for(File f3 : d.listFiles(file->file.getName().contains("Filter"))){
            System.out.println(f3);
        }
    }
}
