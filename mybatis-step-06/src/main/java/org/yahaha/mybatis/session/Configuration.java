package org.yahaha.mybatis.session;

import org.yahaha.mybatis.binding.MapperRegistry;
import org.yahaha.mybatis.mapping.Environment;
import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    protected Environment environment;

    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

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
}
