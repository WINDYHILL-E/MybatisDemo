package org.yahaha.mybatis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.io.Resources;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() throws IOException {
        // 1.从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();

        // 2.获取映射对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 3.验证结果
        String s = userDao.queryUserInfoById(1);
        logger.info(s);
    }
}
