package org.zzr1000.iterableTest;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// refer to : https://www.cnblogs.com/hongwz/p/5510092.html
// 经常使用 for-each循环来遍历来Collection，也可用Iterator
// iterator、iterable关系：
// Iterator是迭代器类，而Iterable是接口。
// 好多类都实现了Iterable接口，这样对象就可以调用iterator()方法。
public class IteratorTest {

    @Test
    public void test(){
        List l = new ArrayList();
        l.add("aa");
        l.add("bb");
        l.add("cc");

        for (Iterator iter = l.iterator(); iter.hasNext();) {
            String str = (String)iter.next();
            System.out.println(str);
        }

        System.out.println("===========");

        Iterator iter = l.iterator();
        while(iter.hasNext()){
            String str = (String) iter.next();
            System.out.println(str);
        }
    }
}
