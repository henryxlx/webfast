package com.jetwinner.webfast.kernel.dao.support;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderBy {

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static final OrderByBuilder builder() {
        return new OrderByBuilder();
    }

    private String columnName;

    private String sortType;

    public OrderBy(String columnName) {
        this(columnName, true);
    }

    public OrderBy(String columnName, boolean ascending) {
        this.columnName = columnName;
        this.sortType = ascending ? OrderBy.ASC : OrderBy.DESC;
    }

    public void alterAsc(String columnName) {
        this.columnName = columnName;
        this.sortType = OrderBy.ASC;
    }

    public void alterDesc(String columnName) {
        this.columnName = columnName;
        this.sortType = OrderBy.DESC;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getSortType() {
        return sortType;
    }
}
