package com.jetwinner.webfast.dao.support.query.part;


import com.jetwinner.webfast.dao.support.query.expression.CompositeExpression;

import java.util.*;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/9
 */
public class SqlPart {
    private final List<String> select = new ArrayList<>();
    private final List<FromAttribute> from = new ArrayList<>();
    private final Map<String, JoinAttribute> join = new HashMap<>();
    private final List<String> set = new ArrayList<>();
    private final ConditionHolder where = new ConditionHolder();
    private final List<String> groupBy = new ArrayList<>();
    private final ConditionHolder having = new ConditionHolder();
    private final List<String> orderBy = new ArrayList<>();

    public void addSelect(String... columns) {
        Collections.addAll(this.select, columns);
    }

    public void addSet(String... sets) {
        Collections.addAll(this.set, sets);
    }

    public void addGroupBy(String... groupBys) {
        Collections.addAll(this.groupBy, groupBys);
    }

    public void addOrderBy(String... orderBys) {
        Collections.addAll(this.orderBy, orderBys);
    }

    public void addWhere(CompositeExpression where) {
        this.where.add(where);
    }

    public void addWhere(Object... where) {
        if (where == null || where.length < 1) {
            return;
        }
        this.where.add(Arrays.asList(where));
    }

    public void addHaving(CompositeExpression where) {
        this.having.add(where);
    }

    public void addHaving(Object... where) {
        if (where == null || where.length < 1) {
            return;
        }
        this.having.add(Arrays.asList(where));
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
        return this.groupBy.size() > 0;
    }

    public ConditionHolder getHaving() {
        return having;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public boolean hasOrderBy() {
        return this.orderBy.size() > 0;
    }

}
