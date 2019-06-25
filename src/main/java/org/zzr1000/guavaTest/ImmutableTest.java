package org.zzr1000.guavaTest;

//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableSet;
//import org.junit.Test;
//
//import java.util.*;
// 所有不可变集合都有一个asList()方法
// 提供ImmutableList视图，来帮助你用列表形式方便地读取集合元素。
public class ImmutableTest {
/*    @Test
    // 虽然unmodifiableList不可以直接添加元素，
    // 但list是可以添加元素的，而list的改变也会使unmodifiableList改变。
    // 所以说Collections.unmodifiableList实现的不是真正的不可变集合。
    public void testJDKImmutable(){
        List<String> list=new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");

        //通过list创建一个不可变的unmodifiableList集合
        List<String> unmodifiableList= Collections.unmodifiableList(list);
        System.out.println(unmodifiableList);

        //通过list添加元素
        list.add("ddd");
        System.out.println("往list添加一个元素:"+list);
        System.out.println("通过list添加元素之后的unmodifiableList:"+unmodifiableList);

        //通过unmodifiableList添加元素
        unmodifiableList.add("eee");
        System.out.println("往unmodifiableList添加一个元素:"+unmodifiableList);
    }

    @Test //copyOf
    public void test4(){
        Set<String> set=new HashSet<String>();
        set.add("we");
        set.add("are");
        set.add("hello");
        ImmutableSet<String> strings = ImmutableSet.copyOf(set);
        System.out.println(strings.asList());
    }

    @Test //of 方法
    public void test5() {
        final ImmutableSet<String> immutableSet = ImmutableSet.of("we", "are", "happy");
        System.out.println(immutableSet);
    }

    @Test // build
    public void test6(){
        ImmutableSet<Object> build = ImmutableSet.builder().add("we").add("are").add("happy").build();
        System.out.println(build);
    }

    @Test //List build
    public void test7() {
        ImmutableList.Builder<String> strListBuilder = ImmutableList.builder();
        strListBuilder.add("aa", "bb", "cc", "dd");
        strListBuilder.add("aa", "bb", "cc", "dd");

        ImmutableList<String> strList = strListBuilder.build();

        System.out.println(strList);

        ImmutableList<Object> strList1 = ImmutableList.builder().add("a").add("b").build();
        ImmutableList<String> strList2 = ImmutableList.<String>builder().add("a").add("b").build();

        System.out.println(strList1);
        System.out.println("asList:" + strList1.asList());
        System.out.println(strList2);

    }*/
}


