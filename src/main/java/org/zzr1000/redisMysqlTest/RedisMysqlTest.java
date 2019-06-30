package org.zzr1000.redisMysqlTest;

import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RedisMysqlTest {

    public static void main(String[] args) throws SQLException {


        Mysql mysql=new Mysql();
        Redis redis=new Redis();
        ResultSet rs=null;

        //正常业务的ID是通过UI的request.getParamenter()获取
        String id="9028935b527d22cc01527d235aea0142";
        String sql="select * from user where id_='" + id + "'";

        String username;

        if(redis.hexists("user_"+id, "username_")){
            username=redis.hget("user_"+id, "username_");
            System.out.println("Welcome Redis! User "+username+" login success");
        }else{
            rs=mysql.conn.createStatement().executeQuery(sql);
            if(rs.next()==false){
                System.out.println("Mysql no register, Please register first");
            }else{
                username=rs.getString("username_");
                System.out.println("Welcome Mysql ! User "+username+" login success");
                //这种小的用户名、密码的信息， 可以使用hash数据结构存储
                //对于大的数据集的话，可以直接将结果集转成json数组
                //对于key来说，这个demo中key比较简单，可以直接将业务内容，当key存储、查询
                //如果是普通的查询语句，可以将查询语句取散列之后，比如md5之后，在存储作为key值
                //在复杂一些的情况，可以对语句中的换行、空格等情况，做替换、标准化，在对比语句key值
                //这样，可以更完善
                redis.hset("user_"+id, "username_", username);

                //30分钟未操作就过期
                redis.expire("user_"+id, 1800);
            }
        }
    }
}


class Redis extends Jedis {

    public Jedis redis;

    {redis = new Jedis("xxxxx", 6379);
     redis.auth("root");
    }


    // public static void main(String[] args) {
    // System.out.println(redis.get("name"));
    // System.out.println(redis.keys("*"));
    // // redis.sinter(keys);
    // }


    public String get(String key) {
        return redis.get(key);
    }

    public String set(String key, String value) {
        return redis.set(key, value);
    }

    public Long del(String... keys) {
        return redis.del(keys);
    }

    // 键值增加字符
    public Long append(String key, String str) {
        return redis.append(key, str);
    }

    public Boolean exists(String key) {
        return redis.exists(key);
    }

    // Need research
    public Long setnx(String key, String value) {
        return redis.setnx(key, value);
    }

    public String setex(String key, String value, int seconds) {
        return redis.setex(key, seconds, value);
    }

    public Long setrange(String key, String str, int offset) {
        return redis.setrange(key, offset, str);
    }

    public List<String> mget(String... keys) {
        return redis.mget(keys);
    }

    public String mset(String... keys) {
        return redis.mset(keys);
    }

    public Long msetnx(String... keysvalues) {
        return redis.msetnx(keysvalues);
    }

    public String getset(String key, String value) {
        return redis.getSet(key, value);
    }

    public String hmset(String key, Map<String, String> hash) {
        return redis.hmset(key, hash);
    }

    public Map<String, String> hgetall(String key) {
        return redis.hgetAll(key);
    }

    public String hget(final String key, final String field) {
        return redis.hget(key, field);
    }

    public Long hset(final String key, final String field, final String value) {
        return redis.hset(key, field, value);
    }

    public Long expire(final String key, final int seconds) {
        return redis.expire(key, seconds);
    }

    public Boolean hexists(final String key, final String field) {
        return redis.hexists(key, field);
    }
}

class Mysql {
     Connection conn;
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/spring","root","root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}