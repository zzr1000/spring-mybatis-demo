package org.zzr1000.stringTest;

import org.junit.Test;

public class SplitTest {


    @Test
    public void test(){
        String str="Java string split test";
        String[] strarray=str.split(" ");
        for (int i = 0; i < strarray.length; i++){
            System.out.println(strarray[i]);
        }

        //取最后一个字符
        System.out.println(strarray[strarray.length - 1]);

    }


}
