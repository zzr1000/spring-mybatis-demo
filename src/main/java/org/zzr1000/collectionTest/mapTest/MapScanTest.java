package org.zzr1000.collectionTest.mapTest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapScanTest {

    @Test
    public void test(){

        Map<String,String> map = new HashMap<String,String>();
        map.put("A","a");
        map.put("B","b");
        map.put("C","c");

        //while
        Iterator<String>itKey = map.keySet().iterator();
        while (itKey.hasNext()){
            String k = itKey.next();
            System.out.println(k +":::"+map.get(k));
        }

        //while
        //Entry可以一次性获得key、value两个值。
        //https://zhidao.baidu.com/question/396998395.html
        Iterator<Map.Entry<String,String>> itEntry = map.entrySet().iterator();
        while (itEntry.hasNext()){
            Map.Entry<String,String> entry = itEntry.next();
            System.out.println(entry.getKey() +":::"+entry.getValue());
        }

        //for
//        for(Iterator<Map.Entry<String,String>> iterator =map.entrySet().iterator();iterator.hasNext();){
//            Map.Entry<String,String> entry = itEntry.next();
//            System.out.println(entry.getKey() +":::"+entry.getValue());
//        }

        //for增强for循环：
        for(Map.Entry<String,String> entry: map.entrySet()){
            System.out.println(entry.getKey() +":::"+entry.getValue());
        }

        System.out.println("foreach====");
        //foreach：
        map.entrySet().stream().forEach(entry-> System.out.println(entry.getKey()+":::"+entry.getValue()));
        map.entrySet().forEach(entry-> System.out.println(entry.getKey()+":::"+entry.getValue()));
        map.forEach((k,v)-> System.out.println(k+":::"+v));
    }
}
