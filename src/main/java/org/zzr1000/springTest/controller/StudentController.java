package org.zzr1000.springTest.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zzr1000.springTest.model.StudentInfo;
import org.zzr1000.springTest.service.StudentIService;

@Controller
@MapperScan("org.zzr1000.springTest.mapper")
@ComponentScan({"org.zzr1000.springTest.mapper"})
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentIService studentIService;


    @ApiOperation(value = "student.insert功能测试")
    @PostMapping("/insert")
    public ResponseEntity<JSONObject> insertStudent(@RequestBody StudentInfo studentInfo) {
        JSONObject result = new JSONObject();
        try {
            result = studentIService.insert(studentInfo);
        } catch (Exception e) {
            result.put("state", "Error");
            result.put("msg", "新增失败："+e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}
