package org.zzr1000.redisTest;

import org.junit.Test;
import org.zzr1000.propertyLoadTest.PropertyLoadTest;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisTest {

    public static void main(String[] args) {
        PropertyLoadTest.initProperties("config.properties");
        //连接 Redis 服务
        Jedis jedis = new Jedis(PropertyLoadTest.getConfigValue("hosts"));
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }

    public static Jedis jedis;
    public static void initConnection(){
        PropertyLoadTest.initProperties("config.properties");
        jedis = new Jedis(PropertyLoadTest.getConfigValue("hosts"));
    }

    public static void closeConnection(){
        jedis.close();
    }

    @Test
    public void redisString(){
        initConnection();
        jedis.set("name","zzr1000");
        System.out.println(jedis.get("name"));
        closeConnection();
    }

    @Test
    public void redisList(){
        initConnection();
        jedis.lpush("family", "father");
        jedis.lpush("family", "mother");
        jedis.lpush("family", "mydearson");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("family", 0 ,2);
        for(int i=0; i<list.size(); i++) {
            System.out.println("列表项为: "+list.get(i));
        }
        closeConnection();
    }

    @Test
    public void redisKeys(){
        initConnection();

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }

        closeConnection();
    }
}
