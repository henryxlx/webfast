package com.jetwinner.webfast.kernel.dao.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderByBuilder {

    private final List<OrderByEntry> orderByEntries;

    public OrderByBuilder() {
        this.orderByEntries = new ArrayList<>();
    }

    public OrderByBuilder add(String columnName) {
        return addAsc(columnName);
    }

    public OrderByBuilder addAsc(String columnName) {
        this.orderByEntries.add(new OrderByEntry(columnName));
        return this;
    }

    public OrderByBuilder addDesc(String columnName) {
        this.orderByEntries.add(new OrderByEntry(columnName, false));
        return this;
    }

    public List<OrderByEntry> toList() {
        return this.orderByEntries;
    }
}
