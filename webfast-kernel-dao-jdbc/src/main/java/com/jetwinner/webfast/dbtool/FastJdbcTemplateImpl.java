package com.jetwinner.webfast.dbtool;

import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * @author xulixin
 */
public class FastJdbcTemplateImpl extends org.springframework.jdbc.core.JdbcTemplate implements FastJdbcTemplate {

    private PlatformTransactionManager platformTransactionManager;
    private DefaultTransactionDefinition transactionDefinition;
    private final ThreadLocal<TransactionStatus> transactionStatus = new ThreadLocal<>();

    public FastJdbcTemplateImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void beginTransaction() {
        TransactionStatus tmp = platformTransactionManager.getTransaction(transactionDefinition);
        transactionStatus.set(tmp);
    }

    @Override
    public void commit() {
        TransactionStatus tmp = transactionStatus.get();
        if (tmp == null) {
            throw new RuntimeGoingException("no transaction");
        }
        platformTransactionManager.commit(tmp);
        transactionStatus.remove();
    }

    @Override
    public void rollBack() {
        TransactionStatus tmp = transactionStatus.get();
        if (tmp == null) {
            throw new RuntimeGoingException("no transaction");
        }
        platformTransactionManager.rollback(tmp);
        transactionStatus.remove();

    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        platformTransactionManager = new DataSourceTransactionManager(getDataSource());
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }

    public DefaultTransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public ThreadLocal<TransactionStatus> getTransactionStatus() {
        return transactionStatus;
    }

}