package org.zzr1000.threadTest.atomicTest;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayTest {

    private static int[] v = new int[]{1,2,3};

    public static void main(String[] args) {
        AtomicIntegerArray aia = new AtomicIntegerArray(v);
    }

}
