package com.jetwinner.webfast.kernel.dao.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public final class OrderBy {

    private final List<OrderByEntry> orderByEntries;

    private OrderBy(int capacity) {
        this.orderByEntries = capacity >= 0 && capacity <= 32  ? new ArrayList<>(capacity) : new ArrayList<>();
    }

    public static OrderBy build(int capacity) {
        return new OrderBy(capacity);
    }

    public OrderBy add(String columnName) {
        return addAsc(columnName);
    }

    public OrderBy addAsc(String columnName) {
        this.orderByEntries.add(new OrderByEntry(columnName));
        return this;
    }

    public OrderBy addDesc(String columnName) {
        this.orderByEntries.add(new OrderByEntry(columnName, false));
        return this;
    }

    public List<OrderByEntry> toList() {
        return this.orderByEntries;
    }

    public boolean hasColumnName(String columnName) {
        for (OrderByEntry entry : this.orderByEntries) {
            if (entry.getColumnName().equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (OrderByEntry entry : this.orderByEntries) {
            buf.append(" ").append(entry.getColumnName()).append(" ").append(entry.getSortType()).append(" ");
        }
        return buf.toString();
    }
}
