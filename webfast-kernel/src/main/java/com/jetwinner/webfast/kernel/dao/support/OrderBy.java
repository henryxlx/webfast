package com.jetwinner.webfast.kernel.dao.support;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderBy {

    public static final String SORT_TYPE_ASC = "ASC";
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

    public OrderBy(String columnName, Object sortType) {
        this.alter(columnName, String.valueOf(sortType));
    }

    public static OrderBy[] toArray(Map<String, Object> sortMap) {
        Set<String> keys = sortMap.keySet();
        OrderBy[] orderByArray = new OrderBy[keys.size()];
        int i = 0;
        for (String key : keys) {
            orderByArray[i++] = new OrderBy(key, sortMap.get(key));
        }
        return orderByArray;
    }

    public void alter(String columnName, String sortType) {
        this.columnName = columnName;
        if (sortType == null) {
            this.sortType = "";
        }
        this.sortType = sortType;
    }

    public void setSortType(Object sortType) {
        if (sortType != null) {
            this.sortType = sortType.toString();
        }
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
