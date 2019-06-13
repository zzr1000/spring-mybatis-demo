package org.zzr1000.propertyLoadTest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoadTest {

    private static Properties properties;

    private PropertyLoadTest(){}

    public static void initProperties(String propertyfile) {
        //synchronized (PropertyLoadTest.class)
        //if (properties.size() != 0){
        //    properties.clear();
        //}
        properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(propertyfile);
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfigValue(String key) {
        if (properties.size() == 0) {
            return null;
        }
        return properties.getProperty(key);
    }
}