package org.zzr1000.springTest.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloRestController {

    @ApiOperation(value = "swagger功能测试1")
    @GetMapping("/index/{name}")
    public String index(@PathVariable String name){
        if(null == name){
            name = "boy";
        }

        return "hello world " + name;
    }

    @ApiOperation(value = "swagger功能测试2")
    @GetMapping("/t")
    public Map<String,String> test(){
        Map map = new HashMap<String,String>();
        map.put("A","a");
        map.put("B","b");
        map.put("C","c");

        return map;
    }

}
