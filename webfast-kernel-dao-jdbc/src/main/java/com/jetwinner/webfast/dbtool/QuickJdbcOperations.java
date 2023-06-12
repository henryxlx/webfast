package com.jetwinner.webfast.dbtool;

import org.springframework.jdbc.core.JdbcOperations;

public interface QuickJdbcOperations extends JdbcOperations {

    void beginTransaction();

    void commit();

    void rollBack();
}

