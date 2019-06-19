package org.zzr1000.treeMapTest;


import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

//（1）TreeMap的基本操作 containsKey、get、put 和 remove 的时间复杂度是 log(n) ，TreeMap是非同步的
//（2）TreeMap中默认的排序为升序，如果要改变其排序可以自己写一个Comparator
public class TreeMapTest {

    public static void main(String[] args) {

        TreeMap<String,Integer> map = new TreeMap<String,Integer>(new xbComparator());
        map.put("key_1", 1);
        map.put("key_2", 2);
        map.put("key_3", 3);
        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();
        while(iter.hasNext())
        {
            String key = iter.next();
            System.out.println(" "+key+":"+map.get(key));
        }
    }

}

class xbComparator implements Comparator
{
    public int compare(Object o1,Object o2)
    {
        String i1=(String)o1;
        String i2=(String)o2;
        return -i1.compareTo(i2);
    }
}