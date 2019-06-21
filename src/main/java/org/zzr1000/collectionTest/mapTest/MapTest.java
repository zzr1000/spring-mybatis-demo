package org.zzr1000.collectionTest.mapTest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/*
put与putIfAbsent区别:
put在放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据，
而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
putIfAbsent   如果传入key对应的value已经存在，就返回存在的value，不进行替换。如果不存在，就添加key和value，返回null
*/
public class MapTest {

    @Test
    public void test(){
        /**
         * put
         * key : 1 , value : 王五
         * key : 2 , value : 李四
         */
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"张三");
        map.put(2,"李四");
        map.put(1,"王五");
        map.forEach((key,value) ->{
            System.out.println("key : " + key + " , value : " + value);
        });
    }

    @Test
    public void test1(){
        /**
         * putIfAbsent
         * key : 1 , value : 张三
         * key : 2 , value : 李四
         */
        Map<Integer,String> putIfAbsentMap = new HashMap<>();
        putIfAbsentMap.putIfAbsent(1,"张三");
        putIfAbsentMap.putIfAbsent(2,"李四");
        putIfAbsentMap.putIfAbsent(1,"王五");
        putIfAbsentMap.forEach((key,value) ->{
            System.out.println("key : " + key + " , value : " + value);
        });
    }
}
