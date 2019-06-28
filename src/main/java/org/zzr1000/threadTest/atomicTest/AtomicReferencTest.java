package org.zzr1000.threadTest.atomicTest;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferencTest {

    private static AtomicReference ar = new AtomicReference();

    public static void main(String[] args) {
        User user = new User();
        user.setName("xxxx");

        ar.set(user);

        User updateUser = new User();
        updateUser.setName("yyyy");
        ar.compareAndSet(user,updateUser);
        System.out.println(ar.get());
    }
}

class User{
    private String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
