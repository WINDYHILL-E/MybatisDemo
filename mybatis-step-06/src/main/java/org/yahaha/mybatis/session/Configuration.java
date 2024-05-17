package org.yahaha.mybatis.session;

import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.datasource.druid.DruidDataSourceFactory;
import org.yahaha.mybatis.datasource.pooled.PooledDataSourceFactory;
import org.yahaha.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.yahaha.mybatis.executor.Executor;
import org.yahaha.mybatis.executor.SimpleExecutor;
import org.yahaha.mybatis.executor.resultset.DefaultResultSetHandler;
import org.yahaha.mybatis.executor.resultset.ResultSetHandler;
import org.yahaha.mybatis.executor.statement.PreparedStatementHandler;
import org.yahaha.mybatis.executor.statement.StatementHandler;
import org.yahaha.mybatis.mapping.BoundSql;
import org.yahaha.mybatis.mapping.Environment;
import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.transaction.Transaction;
import org.yahaha.mybatis.transaction.jdbc.JdbcTransactionFactory;
import org.yahaha.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    protected Environment environment;

    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);

        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }
}
