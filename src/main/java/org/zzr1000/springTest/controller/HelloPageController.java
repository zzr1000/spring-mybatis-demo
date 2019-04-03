package org.zzr1000.springTest.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloPageController {

    @ApiOperation(value = "thymeleaf功能测试")
    @RequestMapping("/testpage")
    public String test(){
        return "index";
    }
}
