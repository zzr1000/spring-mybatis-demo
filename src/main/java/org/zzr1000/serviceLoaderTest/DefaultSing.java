package org.zzr1000.serviceLoaderTest;

import com.google.auto.service.AutoService;

@AutoService(Sing.class)
public class DefaultSing implements Sing{
    @Override
    public void sing() {
        System.out.println("Sing a song...");
    }
}
