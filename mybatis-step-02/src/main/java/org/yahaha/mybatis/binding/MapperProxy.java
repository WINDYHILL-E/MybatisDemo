package org.yahaha.mybatis.binding;

import org.yahaha.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    private final SqlSession sqlSession;

    private final Class<?> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<?> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        return sqlSession.selectOne(method.getName(), args);
    }
}
