package org.zzr1000.springTest.service;

import com.alibaba.fastjson.JSONObject;
import org.zzr1000.springTest.model.StudentInfo;

import java.util.List;

public interface StudentIService {

    public JSONObject insert(StudentInfo studentInfo);

    public void update(StudentInfo studentInfo);

    public List<StudentInfo> find(String name);

    public void delete(String name);

}
