package org.zzr1000.springTest.service;

import com.alibaba.fastjson.JSONObject;
import org.zzr1000.springTest.model.StudentInfo;

public interface StudentIService {

    public JSONObject insert(StudentInfo studentInfo);
    public void update(StudentInfo studentInfo);

    public StudentInfo find(String name);

    public void delete(String name);

}
