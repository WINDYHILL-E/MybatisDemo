package org.yahaha.mybatis.session.defaults;

import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private final MapperRegistry registry;

    public DefaultSqlSession(MapperRegistry registry) {
        this.registry = registry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("执行了语句：" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameters) {
        return (T) ("执行了语句：" + statement + " 执行参数：" + parameters);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return registry.getMapper(type, this);
    }
}
