package com.jetwinner.webfast.dao.support;

/**
 * Created by x230-think-joomla on 2015/6/19.
 */
public class OrderBy {

    public static final String SORT_TYPE_DESC = "DESC";

    private String columnName;

    private String sortType;

    public OrderBy(String columnName) {
        this.columnName = columnName;
        this.sortType = "";
    }

    public OrderBy(String columnName, String sortType) {
        this.alter(columnName, sortType);
    }

    public void alter(String columnName, String sortType) {
        this.columnName = columnName;
        if (sortType == null) {
            this.sortType = "";
        }
        this.sortType = sortType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
