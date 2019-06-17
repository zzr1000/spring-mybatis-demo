package org.zzr1000.fasterxmlTest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class Person2 {

    private String name;
    private String address;
    private String mobile;

    @JsonCreator
    public Person2(@JsonProperty("name") String name,
                   @JsonProperty("address") String address,
                   @JsonProperty("mobile") String mobile) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
    }

  //private Person2() {}

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(ALL, NONE)
                .setVisibility(FIELD, ANY);
        Person2 person = new Person2("name", "address", "mobile");

        //序列化：OK:.
        System.out.println(objectMapper.writeValueAsString(person));

        //反序列化:Error:.
        //Can not construct instance of org.zzr1000.fasterxmlTest.Person2:
        //no suitable constructor found
        //这是因为ObjectMapper在为字段设值之前，无法初始化Person对象，此时有两种解决方式：
        //1.增加无参构造函数
        //2.在已有构造函数上加上@JsonCreator注解，通常与@JsonProperty一起使用
        Person2 p1 = objectMapper.readValue("{\"name\":\"davenkin\",\"address\":\"\"}", Person2.class);
        System.out.println("name: " + p1.name);
        System.out.println("address: " + p1.address);
        System.out.println("mobile: " + p1.mobile);


    }

}
