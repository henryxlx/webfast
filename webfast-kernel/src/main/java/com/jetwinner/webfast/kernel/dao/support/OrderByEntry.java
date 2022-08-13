package com.jetwinner.webfast.kernel.dao.support;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderByEntry {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private String columnName;

    private String sortType;

    public OrderByEntry(String columnName) {
        this(columnName, true);
    }

    public OrderByEntry(String columnName, boolean ascending) {
        this.columnName = columnName;
        this.sortType = ascending ? OrderByEntry.ASC : OrderByEntry.DESC;
    }

    public void alterAsc(String columnName) {
        this.columnName = columnName;
        this.sortType = OrderByEntry.ASC;
    }

    public void alterDesc(String columnName) {
        this.columnName = columnName;
        this.sortType = OrderByEntry.DESC;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getSortType() {
        return sortType;
    }
}
