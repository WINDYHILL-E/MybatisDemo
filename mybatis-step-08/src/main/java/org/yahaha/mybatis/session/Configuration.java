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
import org.yahaha.mybatis.reflaction.MetaObject;
import org.yahaha.mybatis.reflaction.factory.DefaultObjectFactory;
import org.yahaha.mybatis.reflaction.factory.ObjectFactory;
import org.yahaha.mybatis.reflaction.wrapper.DefaultObjectWrapperFactory;
import org.yahaha.mybatis.reflaction.wrapper.ObjectWrapperFactory;
import org.yahaha.mybatis.scripting.LanguageDriverRegistry;
import org.yahaha.mybatis.scripting.xmltags.XMLLanguageDriver;
import org.yahaha.mybatis.transaction.Transaction;
import org.yahaha.mybatis.transaction.jdbc.JdbcTransactionFactory;
import org.yahaha.mybatis.type.TypeAliasRegistry;
import org.yahaha.mybatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configuration {

    //环境
    protected Environment environment;

    // 映射注册机
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    // 映射的语句，存在Map里
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

    // 类型处理器注册机
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    // 对象工厂和对象包装器工厂
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected final Set<String> loadedResources = new HashSet<>();

    protected String databaseId;

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);

        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);

        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
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

    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * 创建结果集处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }

    // 创建元对象
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }

    // 类型处理器注册机
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }
}
