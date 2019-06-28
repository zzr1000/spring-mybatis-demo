package org.zzr1000.threadTest.atomicTest;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

    private static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai);
        System.out.println(ai.addAndGet(1));
        System.out.println(ai);
        System.out.println(ai.compareAndSet(2,4));
        System.out.println(ai);
        System.out.println(ai.get());

        System.out.println(ai.getAndIncrement());
        System.out.println(ai);

        ai.lazySet(8);
        System.out.println(ai.get());

    }

}
