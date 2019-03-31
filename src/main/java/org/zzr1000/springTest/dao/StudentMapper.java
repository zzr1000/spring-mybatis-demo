package org.zzr1000.springTest.dao;


import org.zzr1000.springTest.model.StudentInfo;

//@Mapper
public interface StudentMapper {

     void insert(StudentInfo studentInfo);

     void update(StudentInfo studentInfo);

     void delete(String name);

     StudentInfo find(String name);

}
