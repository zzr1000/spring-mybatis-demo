package org.zzr1000.collectionTest.listTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {

    }

    //List初始化1
    @Test
    public void fListTest1() {
        List<String> name = new ArrayList();
        name.add("xxx");
        name.add("yyy");
        name.add("zzz");

        for(String i:name){
            System.out.println(i);
        }
    }

    //List初始化2
    @Test
    public void fListTest2() {
        List<String> name = Arrays.asList("xxx","yyy","zzz");//长度固定
        for(String i:name){
            System.out.println(i);
        }
    }

    //List初始化3
    @Test
    public void fListTest3() {//长度不固定
        List<String> name = new ArrayList<>(Arrays.asList("xxx","yyy","zzz"));
        for(String i:name){
            System.out.println(i);
        }
    }
}
