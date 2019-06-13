package org.zzr1000.propertyLoadTest;

public class PropertyUseTest {

    public static void main(String[] args) {

        PropertyLoadTest.initProperties("config.properties");

        System.out.println(PropertyLoadTest.getConfigValue("url"));

    }


}
