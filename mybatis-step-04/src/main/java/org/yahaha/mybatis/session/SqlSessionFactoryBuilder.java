package org.yahaha.mybatis.session;

import org.yahaha.mybatis.builder.xml.XMLConfigBuilder;
import org.yahaha.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        return build(new XMLConfigBuilder(reader).parse());
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
