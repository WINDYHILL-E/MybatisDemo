package org.yahaha.mybatis.session.defaults;

import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了 " + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        // TODO 这里传的msId有点不一样，按理说该是namespace + methodName
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        return (T) ("你被代理了！" + "\n方法：" + statement + "\n入参：" + parameter + "\n待执行SQL：" + mappedStatement.getSql());
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
