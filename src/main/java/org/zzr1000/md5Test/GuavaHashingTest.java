package org.zzr1000.md5Test;

import com.google.common.hash.Hashing;
import org.junit.Test;

public class GuavaHashingTest {

    @Test
    public void test() {
        String input = "abc001";
        // 计算MD5
        System.out.println(Hashing.md5().hashBytes(input.getBytes()).toString());
//        // 计算sha256
//        System.out.println(Hashing.sha256().hashBytes(input.getBytes()).toString());
//        // 计算sha512
//        System.out.println(Hashing.sha512().hashBytes(input.getBytes()).toString());
//        // 计算crc32
//        System.out.println(Hashing.crc32().hashBytes(input.getBytes()).toString());

        System.out.println(Hashing.md5().hashUnencodedChars(input).toString());

    }
}
