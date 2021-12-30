package com.jetwinner.webfast.dao.support.query.part;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/9
 */
public class FromAttribute {

    private String table;

    private String alias;

    public FromAttribute(String table, String alias) {
        this.table = table;
        this.alias = alias;
    }

    public String getTable() {
        return this.table != null ? this.table : "";
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAlias() {
        return this.alias != null ? this.alias : "";
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
