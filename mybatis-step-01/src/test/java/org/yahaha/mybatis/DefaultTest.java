package org.yahaha.mybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.dao.impl.UserDaoImpl;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DefaultTest {

    final Logger logger = LoggerFactory.getLogger(DefaultTest.class);

    @Test
    public void testProxyClass() {
        UserDao userDao = (UserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{UserDao.class},
                (proxy, method, args) -> "你被代理了");
        String username = userDao.queryUsername(1);
        logger.info("测试结果：{}", JSON.toJSONString(username));
    }


    @Test
    public void testProxy() {
        UserDao userDao = (UserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{UserDaoImpl.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return "你被代理啦";
                    }
                });

        String s = userDao.queryUsername(1);
        logger.info(s);
    }

    @Test
    public void testSqlSession() {
        // 1.注册mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("org.yahaha.mybatis.dao");

        // 2.创建sqlSession工厂，获取sqlSession
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3.获取mapper代理实例
        UserDao userDao = sqlSession.getMapper(UserDao.class);

        // 4.检验结果
        String s = userDao.queryUsername(1);
        logger.info(s);
    }

}