package org.zzr1000.collectionTest.arrayTest;

import org.junit.Test;

import java.util.ArrayList;

// array、arryaList区别：
// https://www.toutiao.com/i6707114549872427528/
/*
数组有一个缺点：一旦创建，程序运行期间长度不可以发生改变
ArrayList的长度可以改变。

注意点：
对于ArrayList集合来说，直接打印得到的不是地址值，而是内容。

 */
public class ArrayTest {

    @Test
    public void test(){

        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        //对于ArrayList集合来说，直接打印得到的不是地址值，而是内容。
        //[A, B, C, D]
        System.out.println(list);

        for(String l:list){
            System.out.println(l);
        }

        //元素不能是基本数据类型：必须使用基本类型的包装类
        //JDK1.5之后，自动装箱、拆箱：

        ArrayList<Integer> list2 = new ArrayList<>();

    }

}
