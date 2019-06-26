package org.zzr1000.md5Test;

import java.security.MessageDigest;
import org.bouncycastle.util.encoders.Hex;

// refer to :
// https://www.cnblogs.com/wzhanke/p/4737176.html

public class Md5Test2 {

    public static void main(String[] args) throws Exception {

        MessageDigest md=MessageDigest.getInstance("MD5");

        String password="CraneTower";
        String name="0112345";

        byte nam[]=name.getBytes("utf-8");
        byte psd[]=password.getBytes("utf-8");

        md.update(psd);
        md.update(nam);

        byte encryption[]=md.digest();

        String hexmd5 = new String (Hex.encode(encryption));

        System.out.println(hexmd5);
    }

}
