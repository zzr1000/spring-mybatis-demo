package org.zzr1000.hutoolTest;

import cn.hutool.crypto.SecureUtil;
import com.google.common.hash.Hashing;
import org.junit.Test;

public class SecureUtilTest {

    @Test
    public void test1() {
        System.out.println(SecureUtil.md5("aaa"));
      //System.out.println(SecureUtil.md5(" aaa"));//两个md5值不同：..
        System.out.println(Hashing.md5().hashBytes("aaa".getBytes()).toString());
    }

}
