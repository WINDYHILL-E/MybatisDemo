package org.yahaha.mybatis.binding;

import org.yahaha.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

public class MapperProxyFactory {

    private final Class<?> mapperInterface;

    public MapperProxyFactory(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public <T> T newInstance(SqlSession sqlSession) {
        MapperProxy mapperProxy = new MapperProxy(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
