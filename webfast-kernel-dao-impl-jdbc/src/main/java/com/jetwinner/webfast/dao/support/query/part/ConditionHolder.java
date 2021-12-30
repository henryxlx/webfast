package com.jetwinner.webfast.dao.support.query.part;

import com.jetwinner.webfast.dao.support.query.expression.CompositeExpression;

import java.util.List;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/16
 */
public class ConditionHolder {

    private CompositeExpression expression;

    private List list;

    private boolean isList = false;

    public void addMultiple(Object... args) {
        if (expression != null) {
            expression.addMultiple(args);
        }
    }

    public void add(CompositeExpression expr) {
        this.expression = expr;
        this.isList = false;
    }

    public void add(List list) {
        this.list = list;
        this.isList = true;
    }

    public List getList() {
        return this.list;
    }

    public CompositeExpression getExpression() {
        return this.expression;
    }

    public Boolean isList() {
        return this.isList;
    }

    public boolean isExpression() {
        return !this.isList && this.expression != null;
    }

    public ConditionHolder addFirst(Object... args) {
        if (this.isList) {
            for (Object obj : args) {
                this.list.add(obj);
            }
        }
        return this;
    }

    public String toString() {
        if (this.expression != null && !isList) {
            return this.getExpression().toString();
        }
        return "";
    }
}
