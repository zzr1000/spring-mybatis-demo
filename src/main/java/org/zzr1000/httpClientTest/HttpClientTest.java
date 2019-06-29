package org.zzr1000.httpClientTest;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

public class HttpClientTest {

    @Test
    public void test(){
        String url = "http://localhost:8080/user/list/2";
        String result1= HttpUtil.get(url);

        //返回一个json：实现了接口调用：
        //{"msg":"sucess","data":[{"id":2,"username":"BBBB","password":"bbb","age":20}],"state":1}
        System.out.println(result1);
    }

}
