package org.zzr1000.hutoolTest;

import cn.hutool.crypto.SecureUtil;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class SecureUtilTest {

    @Test
    public void test1() {
        System.out.println(SecureUtil.md5("aaa"));
        System.out.println(DigestUtils.md5Hex("aaa"));
        System.out.println(Hashing.md5().hashBytes("aaa".getBytes()).toString());
      //System.out.println(SecureUtil.md5("aaa").substring(24));
      //System.out.println(Integer.valueOf(SecureUtil.md5("aaa").substring(24),16));//转换为10进制，取模：这样会转换失败，取模，可以只取后几位即可
      //System.out.println(Integer.valueOf(SecureUtil.md5("aaa"),16));

        System.out.println(Integer.valueOf("444",16));

      //System.out.println(SecureUtil.md5(" aaa"));//两个md5值不同：..
      //System.out.println(Hashing.md5().hashBytes("aaa".getBytes()).toString());
      //System.out.println(Hashing.md5().hashBytes("aaa".getBytes()).toString());





    }

}
