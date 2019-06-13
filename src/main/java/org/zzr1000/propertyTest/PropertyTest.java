package org.zzr1000.propertyTest;

import org.junit.Test;

import java.io.*;
import java.util.*;

public class PropertyTest {

    @Test
    public void writeProperties() {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("config.properties");
            properties.setProperty("url", "jdbc:mysql://localhost:3306/");
            properties.setProperty("username", "root");
            properties.setProperty("password", "root");
            properties.setProperty("databases", "music_player");
            properties.store(output, "Steven1997 modify" + new Date().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(output!=null) {
                try {
                    output.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            System.out.println("url:" + properties.getProperty("url"));
            System.out.println("username:" + properties.getProperty("username"));
            System.out.println("password:" + properties.getProperty("password"));
            System.out.println("database:" + properties.getProperty("database"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream !=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void printAll() { //遍历
        Properties prop = new Properties();
        InputStream input = null;
        try {
            String file = "config.properties";
//          input = getClass().getClassLoader().getResourceAsStream(file);
            input = new FileInputStream("config.properties");
            prop.load(input);
            if(input == null) {
                System.out.println("无法加载文件" + file);
                return ;
            }
            prop.load(input);
            // 方法一
            Set<Object> keys = prop.keySet();
            for(Object key:keys) {
                System.out.println("key:" + key.toString() + "|" + "value:" + prop.get(key));
            }
            //方法二：
            Set<Map.Entry<Object, Object>> entrys = prop.entrySet();//返回的属性键值对实体
            for(Map.Entry<Object, Object> entry:entrys){
                System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
            }
            //方法三：
            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key:" + key + ",Value:" + value);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(input != null) {
                try {
                    input.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

