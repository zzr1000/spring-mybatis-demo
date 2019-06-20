package org.zzr1000.javaxInjectTest;


//javax.inject.jar：依赖注入非常方便的jar包
//Spring框架依赖注入，必须生成相应类的set方法，而且要在set方法上面写上@Autowired，才能实现依赖注入

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

class TClass{}

class TController{
    private TClass tClass;
    @Autowired
    public void settClass(TClass tClass) {
        this.tClass = tClass;
    }
}

// 每次都要生成相应的set方法比较麻烦
// 如果使用javax.inject.jar，只需要在相应类的属性上面加上@Inject，或者在类的构造方法上
// 自己理解的放在属性上和放在构造方法上的区别：
// 实现功能都一样，但是，当一个类引用多个外部类的时候，
// 如果使用注解属性的话，需要注解多个
// 如果使用构造方法注解话，就只需要一个注解就可以：

public class JavaXInjectTest {

    private final AClass aClass;
    private final BClass bClass;
    private final CClass cClass;
    private final DClass dClass;
    private final EClass eClass;
    private final FClass fClass;

    @Inject // 和google的Inject注解不同，google Inject的使用参考injectTest包demo
    public JavaXInjectTest(AClass aClass,
                           BClass bClass,
                           CClass cClass,
                           DClass dClass,
                           EClass eClass,
                           FClass fClass){
        this.aClass = aClass;
        this.bClass = bClass;
        this.cClass = cClass;
        this.dClass = dClass;
        this.eClass = eClass;
        this.fClass = fClass;

        fClass.printTest();
    }

    //可供其他类初始化JavaXInjectTest该类后，直接调用该方法
    //但是好像不需要Inject，也可以直接调用，这个如果是属性直接Inject，更好解释：. .
    public void test1(){
        fClass.printTest();
    }
}


class AClass{}
class BClass{}
class CClass{}
class DClass{}
class EClass{}
class FClass{

    public void printTest(){
        System.out.println("FClass Injectted!!");
    }

}
