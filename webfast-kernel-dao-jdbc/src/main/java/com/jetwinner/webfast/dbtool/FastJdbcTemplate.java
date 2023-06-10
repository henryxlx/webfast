package com.jetwinner.webfast.dbtool;

import org.springframework.jdbc.core.JdbcOperations;

public interface FastJdbcTemplate extends JdbcOperations {

    void beginTransaction();

    void commit();

    void rollBack();
}

