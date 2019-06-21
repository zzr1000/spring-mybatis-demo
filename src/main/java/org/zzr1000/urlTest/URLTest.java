package org.zzr1000.urlTest;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// refer to : https://www.cnblogs.com/Allen-Wei/p/9084808.html
public class URLTest {

/*
    // 方法一
    URL url = new URL("http://www.sina.com.cn");
    URLConnection urlcon = url.openConnection();
    InputStream is = urlcon.getInputStream();

    // 方法二
    URL url = new URL("http://www.yhfund.com.cn");
    HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
    InputStream is = urlcon.getInputStream();

    //方法三
    URL url = new URL("http://www.yhfund.com.cn");
    InputStream is = url.openStream();
*/

    @Test
    public void test() throws IOException {

        long begintime = System.currentTimeMillis();
        URL url = new URL("https://www.baidu.com/");
        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
        urlcon.connect();         //获取连接
        InputStream is = urlcon.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        StringBuffer bs = new StringBuffer();
        String l = null;
        while((l=buffer.readLine())!=null){
            bs.append(l).append("/n");
        }
        System.out.println(bs.toString());
        System.out.println("总共执行时间为："+(System.currentTimeMillis()-begintime)+"毫秒");
    }

}
