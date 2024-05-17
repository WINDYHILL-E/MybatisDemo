package org.yahaha.mybatis.executor;

import org.yahaha.mybatis.mapping.BoundSql;
import org.yahaha.mybatis.mapping.MappedStatement;
import org.yahaha.mybatis.session.ResultHandler;
import org.yahaha.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}
