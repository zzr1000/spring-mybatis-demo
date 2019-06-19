package org.zzr1000.guavaTest;

import com.google.common.base.Preconditions;
import org.junit.Test;

// Preconditions是guava提供的用于进行代码校验的工具类，
// 其中提供了许多重要的静态校验方法，用来简化我们工作或开发中对代码的校验或预处理，
// 能够确保代码符合我们的期望，并且能够在不符合校验条件的地方，准确的为我们显示出问题所在
// https://blog.csdn.net/dyllove98/article/details/9140523
//1、checkArgument：检查传入参数是否为true
//2、checkNotNull：检查传入参数是否为null
//3、checkState：检查对象状态
public class PreconditionsTest {

    @Test
    public void Preconditions() throws Exception {

        getPersonByPrecondition(8,"peida");

        try {
            getPersonByPrecondition(-9,"peida");
        } catch (Exception e) {//age 必须大于0
            System.out.println(e.getMessage());
        }

        try {
            getPersonByPrecondition(8,"");
        } catch (Exception e) {//name为''
            System.out.println(e.getMessage());
        }

        try {
            getPersonByPrecondition(8,null);
        } catch (Exception e) {//name为null
            System.out.println(e.getMessage());
        }
    }

    public static void getPersonByPrecondition(int age,String name)throws Exception {
        Preconditions.checkNotNull(name, "name为null");
        Preconditions.checkArgument(name.length() > 0, "name为\'\'");
        Preconditions.checkArgument(age > 0, "age 必须大于0");
        System.out.println("a person age:" + age + ",name:" + name);
    }
    
}
