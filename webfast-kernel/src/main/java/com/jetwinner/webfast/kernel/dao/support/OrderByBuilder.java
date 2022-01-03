package com.jetwinner.webfast.kernel.dao.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderByBuilder {

    private final List<OrderBy> orderByList;

    public OrderByBuilder() {
        this.orderByList = new ArrayList<>();
    }

    public OrderByBuilder add(String columnName) {
        return addAsc(columnName);
    }

    public OrderByBuilder addAsc(String columnName) {
        this.orderByList.add(new OrderBy(columnName));
        return this;
    }

    public OrderByBuilder addDesc(String columnName) {
        this.orderByList.add(new OrderBy(columnName, false));
        return this;
    }

    public List<OrderBy> toList() {
        return this.orderByList;
    }
}
