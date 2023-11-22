package org.yahaha.mybatis.session.defaults;

import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry registry;

    public DefaultSqlSessionFactory(MapperRegistry registry) {
        this.registry = registry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(registry);
    }
}
