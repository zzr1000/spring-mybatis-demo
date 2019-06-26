package org.zzr1000.randomTest;

import org.junit.Test;

import java.util.UUID;

public class UUIDTest {

    @Test
    public void test(){
        for(int i=0;i<10;i++){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(uuid);
        }
    }
}
