package com.jetwinner.webfast.dao.support.query.part;


import com.jetwinner.webfast.dao.support.query.expression.CompositeExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/9
 */
public class SqlPart {
    private List<String> select = new ArrayList<String>();
    private List<FromAttribute> from = new ArrayList<FromAttribute>();
    private Map<String, JoinAttribute> join = new HashMap<String, JoinAttribute>();
    private List<String> set = new ArrayList<String>();
    private ConditionHolder where = new ConditionHolder();
    private List<String> groupBy = new ArrayList<String>();
    private ConditionHolder having = new ConditionHolder();
    private List<String> orderBy = new ArrayList<String>();

    public void addSelect(String... columns) {
        for (String value : columns) {
            this.select.add(value);
        }
    }

    public void addSet(String... sets) {
        for (String value : sets) {
            this.set.add(value);
        }
    }

    public void addGroupBy(String... groupBys) {
        for (String value : groupBys) {
            this.groupBy.add(value);
        }
    }

    public void addOrderBy(String... orderBys) {
        for (String value : orderBys) {
            this.orderBy.add(value);
        }
    }

    public void addWhere(CompositeExpression where) {
        this.where.add(where);
    }

    public void addWhere(Object... where) {
        if (where == null || where.length < 1) {
            return;
        }
        List list = new ArrayList();
        for (Object obj : where) {
            list.add(obj);
        }
        this.where.add(list);
    }

    public void addHaving(CompositeExpression where) {
        this.having.add(where);
    }

    public void addHaving(Object... where) {
        if (where == null || where.length < 1) {
            return;
        }
        List list = new ArrayList();
        for (Object obj : where) {
            list.add(obj);
        }
        this.having.add(list);
    }

    public void addFrom(FromAttribute fromAttribute) {
        this.from.add(fromAttribute);
    }

    public void addJoin(String fromAlias, JoinAttribute joinAttribute) {
        this.join.put(fromAlias, joinAttribute);
    }

    public void reset(String queryPartName) {
        String key = queryPartName.toLowerCase();

    }

    public List<String> getSelect() {
        return select;
    }

    public List<FromAttribute> getFrom() {
        return from;
    }

    public Map<String, JoinAttribute> getJoin() {
        return join;
    }

    public List<String> getSet() {
        return set;
    }

    public ConditionHolder getWhere() {
        return where;
    }

    public List<String> getGroupBy() {
        return groupBy;
    }

    public boolean hasGroupBy() {
        return this.groupBy != null && this.groupBy.size() > 0;
    }

    public ConditionHolder getHaving() {
        return having;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public boolean hasOrderBy() {
        return this.orderBy != null && this.orderBy.size() > 0;
    }

}
