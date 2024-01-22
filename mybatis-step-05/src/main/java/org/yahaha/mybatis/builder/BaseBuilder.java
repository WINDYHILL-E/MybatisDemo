package org.yahaha.mybatis.builder;

import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.type.TypeAliasRegistry;

public class BaseBuilder {

    protected final Configuration configuration;

    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
