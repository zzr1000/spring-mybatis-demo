package org.zzr1000.httpClientTest;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientTest {

    @Test
    public void test(){
        String url = "http://localhost:8080/user/list/3";
        String result1= HttpUtil.get(url);

        //返回一个json：实现了接口调用：
        //{"msg":"sucess","data":[{"id":2,"username":"BBBB","password":"bbb","age":20}],"state":1}
        System.out.println(result1);
    }


    @Test
    public void test2(){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        System.out.println(HttpUtil.get("https://www.baidu.com",paramMap));
    }




}
