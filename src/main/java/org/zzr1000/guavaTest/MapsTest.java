package org.zzr1000.guavaTest;

import com.google.common.collect.Maps;

import java.util.Map;

//ReferTo : https://www.cnblogs.com/qdhxhz/p/9429769.html
public class MapsTest {

    public static void main(String[] args) {

        //1、Maps.newHashMap()获得HashMap();
        Map<Integer, Integer> map0 = Maps.newHashMap();
        for (int i = 0; i < 10; i++) {
            map0.put(i, i);
        }
        System.out.println("map0：" + map0);

        //2、传入map0参数构建map
        Map<Integer, Integer> map1 = Maps.newHashMap(map0);
        map1.put(10, 10);
        System.out.println("map1：" + map1);


        //3、使用条件：确定容器会装多少个，不确定就用一般形式的
        //这个容器超过3个还是会自动扩容的。默认是分配一个容量为16的数组，不够将扩容
        Map<Integer, Integer> map2 = Maps.newHashMapWithExpectedSize(3);
        map2.put(1, 1);
        map2.put(2, 2);
        map2.put(3, 3);
        System.out.println("map2：" + map2);

    }
}
