package org.yahaha.mybatis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yahaha.mybatis.binding.MapperProxyFactory;
import org.yahaha.mybatis.dao.UserDao;
import org.yahaha.mybatis.dao.impl.UserDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(s);
    }

    @Test
    public void testMapperProxy() {
        Map<String, String> sqlSession = new HashMap<>();
        sqlSession.put("org.yahaha.mybatis.dao.UserDao.queryUsername","select * from db;");
        MapperProxyFactory<UserDao> userDaoMapperProxyFactory = new MapperProxyFactory<>(UserDao.class);
        UserDao userDao = userDaoMapperProxyFactory.newProxyInstance(sqlSession);
        String s = userDao.queryUsername(1);
        System.out.println(s);
    }

}