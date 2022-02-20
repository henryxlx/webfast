package com.jetwinner.webfast.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author xulixin
 */
public class DummyDateSource implements DataSource {

    public static final String NOT_VALID_DATA_SOURCE_MSG = "Not a valid data source!";

    private static final DummyDateSource INSTANCE = new DummyDateSource();

    private DummyDateSource() {
        // reserved.
    }

    public static DummyDateSource getInstance() {
        return INSTANCE;
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException(NOT_VALID_DATA_SOURCE_MSG);
    }
}