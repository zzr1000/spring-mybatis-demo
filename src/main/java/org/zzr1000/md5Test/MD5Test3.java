package org.zzr1000.md5Test;

import cn.hutool.crypto.SecureUtil;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Test3 {// 三种生成md5方式：hutool、google、commons

    public static void main(String[] args) {

        System.out.println(SecureUtil.md5("aaa"));
        System.out.println(DigestUtils.md5Hex("aaa"));
        System.out.println(Hashing.md5().hashBytes("aaa".getBytes()));
    }
}
