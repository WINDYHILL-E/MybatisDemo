package org.yahaha.mybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.io.Resources;
import org.yahaha.mybatis.po.User;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void testSqlSessionFactory() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }
}
