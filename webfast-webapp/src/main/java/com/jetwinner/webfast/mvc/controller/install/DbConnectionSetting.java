package com.jetwinner.webfast.mvc.controller.install;

/**
 * @author xulixin
 */
public class DbConnectionSetting {

    private String host;
    private String port;
    private String user;
    private String password;
    private String dbname;
    private Boolean replaceDatabase;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public Boolean getReplaceDatabase() {
        return replaceDatabase;
    }

    public void setReplaceDatabase(Boolean replaceDatabase) {
        this.replaceDatabase = replaceDatabase;
    }
}
