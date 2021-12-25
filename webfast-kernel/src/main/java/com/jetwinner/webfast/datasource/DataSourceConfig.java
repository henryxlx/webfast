package com.jetwinner.webfast.datasource;

import java.io.File;

/**
 * @author xulixin
 */
public interface DataSourceConfig {

    String MYSQL_NEW_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    String MYSQL_OLD_DRIVER_CLASS = "com.mysql.jdbc.Driver";

    String JDBC_URL_MYSQL_HOST_FORMATTER = "jdbc:mysql://%s:%s";
    String JDBC_URL_DATABASE_SETTING_FORMATTER = "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    String DATA_SOURCE_CONFIG_FILE_NAME = "datasource.yml";
    String DATA_SOURCE_CONFIG_FILE_DIR = File.separator + "config" + File.separator;
    String DATA_SOURCE_CONFIG_FILE_LOC = DATA_SOURCE_CONFIG_FILE_DIR + DATA_SOURCE_CONFIG_FILE_NAME;

    static String getMysqlJdbcUrl(String dbHost, String dbPort) {
        return getMysqlJdbcUrl(dbHost, dbPort, null);
    }

    static String getMysqlJdbcUrl(String dbHost, String dbPort, String dbName) {
        StringBuilder buf = new StringBuilder();
        buf.append(JDBC_URL_MYSQL_HOST_FORMATTER);
        if (dbName != null) {
            buf.append("/%s");
        }
        buf.append(JDBC_URL_DATABASE_SETTING_FORMATTER);
        return String.format(buf.toString(), dbHost, dbPort, dbName);
    }

    boolean getDataSourceDisabled();

    void setDataSourceDisabled(boolean dataSourceDisabled);

    void reloadDataSource();
}
