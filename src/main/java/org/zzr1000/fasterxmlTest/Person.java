package org.zzr1000.fasterxmlTest;
//refer to : https://www.jianshu.com/p/4bd355715419

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Data
public class Person {
    private String name;
    private String address;
    private String mobile;

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person();
        person.setName("davenkin");
        person.setAddress("");
        //{"name":"davenkin","address":"","mobile":null}
        System.out.println(objectMapper.writeValueAsString(person));

        //反序列化
        //OjbectMapper是通过反射的机制，
        //通过调用Json中字段所对应的setter方法进行反序列化的。(通过getter实现序列化)
        //并且此时，依赖于Person上有默认构造函数。
        Person p1 = objectMapper.readValue("{\"name\":\"davenkin\",\"address\":\"\"}", Person.class);

        /*打印如下值：
        * name: davenkin
        * address:
        * mobile: null
        * */
        System.out.println("name: " + p1.getName());
        System.out.println("address: " + p1.getAddress());
        System.out.println("mobile: " + p1.getMobile());

        //去除掉对getter和setter的依赖：序列化/反序列化类中可以不需要getter/setter方法
        //Person2类中细讲
        objectMapper.setVisibility(ALL, NONE)
                .setVisibility(FIELD, ANY);

    }

}
