package org.yahaha.mybatis.session.defaults;

import org.yahaha.mybatis.executor.Executor;
import org.yahaha.mybatis.mapping.Environment;
import org.yahaha.mybatis.session.Configuration;
import org.yahaha.mybatis.session.SqlSession;
import org.yahaha.mybatis.session.SqlSessionFactory;
import org.yahaha.mybatis.session.TransactionIsolationLevel;
import org.yahaha.mybatis.transaction.Transaction;
import org.yahaha.mybatis.transaction.TransactionFactory;

import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            final Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }
}
