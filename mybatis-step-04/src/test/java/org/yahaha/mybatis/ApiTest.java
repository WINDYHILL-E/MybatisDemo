package org.yahaha.mybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.builder.xml.XMLConfigBuilder;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.io.Resources;
import org.yahaha.mybatis.po.User;
import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.SqlSessionFactoryBuilder;
import org.yahaha.mybatis.session.defaults.DefaultSqlSession;

import java.io.IOException;
import java.io.Reader;

public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void testSqlSessionFactory() throws IOException {
        // 1.从sqlSessionFactory中获取sqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourcesAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2.获取映射器对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 3.验证结果
        User user = userDao.queryUserInfoById(1);
        logger.info("测试结果 {}", JSON.toJSONString(user));
    }

    @Test
    public void testSelectOne() throws IOException {
        Reader reader = Resources.getResourcesAsReader("mybatis-config-datasource.xml");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        Configuration configuration = xmlConfigBuilder.parse();

        SqlSession sqlSession = new DefaultSqlSession(configuration);

        Object[] req = {1L};
        Object res = sqlSession.selectOne("org.yahaha.mybatis.dao.UserDao.queryUserInfoById", req);
        logger.info("测试结果：{}", JSON.toJSONString(res));
    }

}
