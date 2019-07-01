package org.zzr1000.mybatisTest.test2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zzr1000.mybatisTest.test2.dao.User2mapper;
import org.zzr1000.mybatisTest.test2.model.User2;

/**
 * refer to :
 * https://www.cnblogs.com/Ankermaker/p/6930903.html
 */

public class MybatisTest {


    private SqlSessionFactory sessionFactory;
    private static SqlSession session;
    private User2mapper mapper;

    @Before
    public void setup() {
        String resource = "mapper/confForTest2.xml";
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(is);
            session = sessionFactory.openSession();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getObjects() {
        // 分页查询
        // 获取 映射 借口 对象 ;
        mapper = session.getMapper(User2mapper.class);
        List<User2> list = mapper.getObjects();

        for (User2 u : list) {
            System.out.println(u + "测试成功");
        }
    }

    @After
    public void result() {
        if (sessionFactory != null) {
            sessionFactory = null;
        }
        if (session != null) {
            session = null;

        }
    }

}
