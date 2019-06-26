package org.zzr1000.typeTest;

import org.junit.Test;

// refer to
// https://www.cnblogs.com/keeplearnning/p/7003415.html
public class StringToByte {

    @Test
    public void test(){
        //Original String
        String string = "hello world";

        //Convert to byte[]
        byte[] bytes = string.getBytes();

        //Convert back to String
        String s = new String(bytes);

        //Check converted string against original String
        System.out.println("Decoded String : " + s);
    }

}
