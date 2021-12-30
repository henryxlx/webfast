package com.jetwinner.webfast.dao.support.query.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite expression is responsible to build a group of similar expression.
 * <p/>
 * Created by x230-think-joomla on 2015/6/8.
 */
public class CompositeExpression {
    /**
     * Constant that represents an AND composite expression
     */
    public static final String TYPE_AND = "AND";

    /**
     * Constant that represents an OR composite expression
     */
    public static final String TYPE_OR = "OR";

    /**
     * string Holds the instance type of composite expression
     */
    private String type;

    /**
     * array Each expression part of the composite expression
     */
    private List parts = new ArrayList();

    private CompositeExpression() {
        this.parts = new ArrayList();
    }

    public CompositeExpression(String type, List parts) {
        this();
        this.type = type;
        this.parts = parts;
    }

    /**
     * Constructor.
     *
     * @param type  Instance type of composite expression
     * @param parts Composition of expressions to be joined on composite expression
     */
    public CompositeExpression(String type, Object... parts) {
        this();
        this.type = type;
        this.addMultiple(parts);
    }

    /**
     * Adds multiple parts to composite expression.
     *
     * @param parts multiple expression
     * @return CompositeExpression
     */
    public CompositeExpression addMultiple(Object... parts) {
        for (Object part : parts) {
            this.add(part);
        }

        return this;
    }

    /**
     * Adds an expression to composite expression.
     *
     * @param part an expression object
     * @return CompositeExpression
     */
    public CompositeExpression add(Object part) {
        if (part != null || (part instanceof CompositeExpression && ((CompositeExpression) part).count() > 0)) {
            this.parts.add(part);
        }

        return this;
    }

    /**
     * Retrieves the amount of expressions on composite expression.
     *
     * @return integer
     */
    public int count() {
        return this.parts != null ? this.parts.size() : 0;
    }

    /**
     * Retrieve the string representation of this composite expression.
     *
     * @return string
     */
    public String toString() {
        if (count() == 1) {
            return this.parts.get(0).toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.parts.size(); i++) {
            if (i > 0) {
                sb.append(") ").append(this.type).append(" (");
            }
            sb.append(this.parts.get(i));
        }

        sb.append(")");

        return sb.toString();
    }

    /**
     * Return type of this composite expression (AND/OR)
     *
     * @return string
     */
    public String getType() {
        return this.type;
    }
}
