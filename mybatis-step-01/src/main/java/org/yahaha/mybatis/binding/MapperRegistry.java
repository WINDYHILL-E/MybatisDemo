package org.yahaha.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import org.yahaha.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperRegistry {

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public void addMappers(String packageName) {
        Set<Class<?>> mapperClasses = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperClasses) {
            addMapper(mapperClass);
        }
    }

    // private void addMapper(Class<?> type) {
    private <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMappers(type)) {
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    private boolean hasMappers(Class<?> type) {
        return knownMappers.containsKey(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }
}
