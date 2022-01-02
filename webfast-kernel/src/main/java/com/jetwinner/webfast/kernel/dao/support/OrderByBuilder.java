package com.jetwinner.webfast.kernel.dao.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author x230-think-joomla
 * @date 2015/6/19
 */
public class OrderByBuilder {

    private final List<OrderBy> orderByList;

    public OrderByBuilder() {
        this.orderByList = new ArrayList<>();
    }

    public static OrderBy[] toArray(Map<String, Object> sortMap) {
        Set<String> keys = sortMap.keySet();
        OrderBy[] orderByArray = new OrderBy[keys.size()];
        int i = 0;
        for (String key : keys) {
            OrderBy orderBy = OrderBy.DESC.equals(sortMap.get(key)) ?
                    new OrderBy(key, false) : new OrderBy(key);
            orderByArray[i++] = orderBy;
        }
        return orderByArray;
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

    public OrderBy[] toArray() {
        return this.orderByList.toArray(new OrderBy[0]);
    }
}
