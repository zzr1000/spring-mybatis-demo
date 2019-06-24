package org.zzr1000.genericTest;

//可以在泛型类或者非泛型类（或者泛型接口、非泛型接口）中声明泛型方法
public class GenericTestFunction {



}

class A{
    public void m(){
        System.out.println("Hello");
    }
}


class B{
    public <V> void m(){
        System.out.println("Hello");
    }
}

class C{
    public <V> void m(V v){
        System.out.println("Hello");
    }
}

class D{
    public <V> V m(V v){
        System.out.println("Hello");
        return null;
    }
}




