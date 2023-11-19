package org.yahaha.mybatis.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 代理工厂-->拿到代理类-->代理类调用方法
 */
public class MapperProxy<T> implements InvocationHandler {

    private Map<String, String> sqlSession;

    private Class<T> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // todo Object.class.equals(method.getDeclaringClass()) 这是什么写法？
        if (Object.class.equals(method.getDeclaringClass())) {
            // todo 为什么是this？
            return method.invoke(this, args);
        } else {
            // todo mapperInterface.getName()+"."+method.getName() ？
            return sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }
}
