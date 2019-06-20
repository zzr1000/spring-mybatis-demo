package org.zzr1000.guiceTest.bInjectDemo2;


import com.google.inject.Guice;
import com.google.inject.Injector;


//不使用new，拿到类实例
//https://blog.csdn.net/nxcatman/article/details/52765455
public class PlayerTest {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        Player player = injector.getInstance(Player.class);
        player.setName("live");
        System.out.println(player);
    }
}

class Player {

    private String name;

    public void setName(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
