package org.yahaha.mybatis.executor;

import org.yahaha.mybatis.executor.statement.StatementHandler;
import org.yahaha.mybatis.mapping.BoundSql;
import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.session.ResultHandler;
import org.yahaha.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {


    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
            Connection connection = transaction.getConnection();
            Statement statement = handler.prepare(connection);
            handler.parameterize(statement);
            return handler.query(statement, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
