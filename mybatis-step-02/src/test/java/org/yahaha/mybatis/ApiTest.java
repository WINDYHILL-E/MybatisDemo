package org.yahaha.mybatis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.defaults.DefaultSqlSessionFactory;


public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() {
        // 1.注册mapper
        MapperRegistry mapperRegistry = new MapperRegistry();
        mapperRegistry.addMappers("org.yahaha.mybatis.dao");

        // 2.创建sqlSession工厂，开启sqlSession
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(mapperRegistry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3.获取代理mapper
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 4.验证执行结果
        String s = userDao.queryName(1);
        logger.info(s);
    }

}
