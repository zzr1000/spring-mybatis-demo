package org.zzr1000.mybatisTest.test1;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

/*
入门级demo：
https://www.cnblogs.com/kakag/p/3140278.html
 */

public class MyBatisTest{

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("user.dir"));
        String resource = "mapper/configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("userTest.selectUser", 3);

        if (user != null) {
            System.out.println(user.getUsername());
        }

        sqlSession.close();
    }
}
