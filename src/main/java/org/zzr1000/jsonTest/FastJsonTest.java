package org.zzr1000.jsonTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/*
refer to :
序列化、反序列化：https://www.jianshu.com/p/26379afb895c
基本使用：https://www.cnblogs.com/wendu/p/6635717.html
 */

public class FastJsonTest {


    @Test
    public void test(){
        String jsonArrayString = "[{\"name\":\"张三\",\"age\":50},{\"name\":\"李四\",\"age\":51}]";
        List<User> userList = JSON.parseArray(jsonArrayString,User.class);
        Iterator it = userList.iterator();
        while (it.hasNext()) {
            User u = (User)it.next();
            System.out.println("name:"+u.getName()+" age:"+u.getAge());
        }
    }


    @Test
    public void test2(){
        String jsonString = "{\"name\":\"张三\",\"age\":50}";
        User user= JSON.parseObject(jsonString,User.class);
        System.out.println("name:"+user.getName()+" age:"+user.getAge());
    }

    @Test
    public void test3(){
        User u = new User();
        u.setName("王五");
        u.setAge(30);
        System.out.println(JSON.toJSONString(u));
    }

    @Test
    public void test4(){
        String jsonString1 = "{\"name\":\"张三\",\"age\":50}";
        JSONObject jsonObject = JSON.parseObject(jsonString1);

        System.out.println(jsonObject.keySet()); // 输出key集合，输出结果 [name, age]

        if(jsonObject.containsKey("sex")) { // 判断key是否存在，输出结果 false
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        jsonObject.put("sex","man"); // 添加k/v键值对，输出结果 {"sex":"man","name":"张三","age":50}
        System.out.println(jsonObject);

        if (jsonObject.containsValue("man")) { // 判断value是否存在，输出结果 false
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

    @Test
    public void test5(){
        String jsonArrayString1 = "[{\"id\":1,\"studentName\":\"lily\",\"age\":12},{\"id\":2,\"studentName\":\"lucy\",\"age\":15}]";
        JSONArray jsonArray = JSON.parseArray(jsonArrayString1);

        for (int i = 0; i < jsonArray.size(); i++) { // 遍历输出
            JSONObject jsonObj= jsonArray.getJSONObject(i);
            System.out.println(jsonObj.get("id"));
        }

        Student s3 = new Student(3,"学生乙",15);
        jsonArray.add(s3); // 添加新jsonobject对象，输出结果 3
        System.out.println(jsonArray.size());

        if(jsonArray.contains(s3)) { // 判断是否存在，输出结果 true
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }



}

@Data
 class User {
     private String name;
     private int age;
 }

 @Data
 class Student{
    public Student(int id,String studentName,int age){
        this.id = id;
        this.studentName = studentName;
        this.age = age;
     }
    private int id;
     private String studentName;
     private int age;
 }
