package org.zzr1000.typeTest;

import java.util.concurrent.atomic.AtomicBoolean;

//用原子方式更新的 boolean 值
//能保证在高并发的情况下只有一个线程能够访问这个属性值。
//一般情况下，使用 AtomicBoolean 高效并发处理 “只初始化一次” 的功能要求
public class AtomicBooleanTest {

    private static AtomicBoolean initialized = new AtomicBoolean(false);

    public void init()
    {
        if( initialized.compareAndSet(false, true) )
        {
            // 这里放置初始化代码....
        }
    }


    //如果没有AtomicBoolean，我们可以使用volatile做如下操作：
    private static volatile boolean initialized1 = false;
    public void init2()
    {
        if( initialized1 == false ){
            initialized1 = true;
            // 这里初始化代码....
        }
    }

}
