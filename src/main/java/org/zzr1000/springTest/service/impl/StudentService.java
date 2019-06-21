package org.zzr1000.springTest.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.zzr1000.springTest.dao.StudentMapper;
import org.zzr1000.springTest.model.StudentInfo;
import org.zzr1000.springTest.service.StudentIService;

import java.util.List;


@Service
@ComponentScan({"org.zzr1000.springTest.dao"})
public class StudentService implements StudentIService {


    @Autowired
    StudentMapper studentMapper;


    @Override
    public JSONObject insert(StudentInfo studentInfo) {
        JSONObject result = new JSONObject();
        studentMapper.insert(studentInfo);

        result.put("state","OK");
        result.put("message","success");
        return result;

    }

    @Override
    public void update(StudentInfo studentInfo) {
        studentMapper.update(studentInfo);
    }

    @Override
    public List<StudentInfo> find(String name) {
        return studentMapper.find(name);
    }

    @Override
    public void delete(String name) {
        studentMapper.delete(name);
    }
}
