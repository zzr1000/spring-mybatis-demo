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

        System.out.println(str.split(" ")[str.split(" ").length - 1]);

        String[] s = "a.b.c".split("\\.");//需要加 双斜杠 转义字符
        System.out.println(s.length);
        for (int i = 0 ; i < s.length ; i++){
            System.out.println(s[i]);
        }


    }


}
