package org.zzr1000.genericTest;

//泛型接口：
public interface GenericTestInterface<T> {
    T test();
}

//具体类实现接口的时候，指定具体数据类型：
class GenericTestClassImpl implements GenericTestInterface<String>{

    @Override
    public String test() {
        return null;
    }
}