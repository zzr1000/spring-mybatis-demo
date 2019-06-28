package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class StandardInputOutputErrorTest {


    public static void main(String[] args) throws IOException {

        System.out.println("xx");
        System.err.println("eeeeeee");

        int num = -1;

        while ((num = System.in.read()) != '\n'){
//            System.out.print(num);
//            System.out.println();
            System.out.print((char) num);
        }
    }


    public void setOutIn() throws FileNotFoundException {
        File f = new File("");
        PrintStream ps = new PrintStream(f);
        System.setOut(ps);//setout的重定向需要一个PrintStream参数
        System.out.println("xxxxxxxxxxxxxx");
    }

}
