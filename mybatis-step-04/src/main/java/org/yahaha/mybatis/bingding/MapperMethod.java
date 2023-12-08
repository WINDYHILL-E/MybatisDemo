package org.yahaha.mybatis.bingding;

import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.mapping.SqlCommandType;
import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.session.SqlSession;

import java.lang.reflect.Method;

public class MapperMethod {

    private final SqlCommand command;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.command = new SqlCommand(mapperInterface, method, configuration);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.type) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                result = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for " + command.getName());
        }
        return result;
    }

    public static class SqlCommand {

        private String name;
        private SqlCommandType type;

        public SqlCommand(Class<?> mapperInterface, Method method, Configuration configuration) {

            String statement = mapperInterface.getName() + "." + method.getName();
            MappedStatement ms = configuration.getMappedStatement(statement);

            this.name = ms.getId();
            this.type = ms.getSqlCommandType();

        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }
}
