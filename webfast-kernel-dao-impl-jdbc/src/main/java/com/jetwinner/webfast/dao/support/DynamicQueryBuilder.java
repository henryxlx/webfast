package com.jetwinner.webfast.dao.support;

import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/8
 */
public class DynamicQueryBuilder extends AbstractQueryBuilder {

    protected Map<String, Object> conditions;

    public DynamicQueryBuilder(Map<String, Object> conditions) {
        this.conditions = conditions;
    }

    @Override
    public DynamicQueryBuilder select(String... select) {
        super.select(select);
        return this;
    }

    @Override
    public DynamicQueryBuilder from(String fromTable, String alias) {
        super.from(fromTable, alias);
        return this;
    }

    @Override
    public DynamicQueryBuilder from(String fromTable) {
        super.from(fromTable);
        return this;
    }

    @Override
    public DynamicQueryBuilder innerJoin(String fromAlias, String joinTable, String alias, String condition) {
        super.innerJoin(fromAlias, joinTable, alias, condition);
        return this;
    }

    @Override
    public DynamicQueryBuilder innerJoin(String fromAlias, String join, String alias) {
        super.innerJoin(fromAlias, join, alias, null);
        return this;
    }

    public DynamicQueryBuilder orderBy(OrderBy orderBy) {
        super.orderBy(orderBy.getColumnName(), orderBy.getSortType());
        return this;
    }

    public DynamicQueryBuilder orderBy(OrderByBuilder builder) {
        if (builder != null) {
            OrderBy[] orderByArray = builder.toArray();
            if (orderByArray != null) {
                for (OrderBy orderBy : orderByArray) {
                    super.orderBy(orderBy.getColumnName(), orderBy.getSortType());
                }
            }
        }
        return this;
    }

    @Override
    public DynamicQueryBuilder setFirstResult(int firstResult) {
        super.setFirstResult(firstResult);
        return this;
    }

    @Override
    public DynamicQueryBuilder setMaxResults(int maxResults) {
        super.setMaxResults(maxResults);
        return this;
    }

    public DynamicQueryBuilder where(String where) {
        if (!isWhereInConditions(where)) {
            return this;
        }
        super.where(where);
        return this;
    }

    public DynamicQueryBuilder andWhere(String where) {
        if (!isWhereInConditions(where)) {
            return this;
        }

        if (isInCondition(where)) {
            return addWhereIn(where);
        }

        super.andWhere(where);
        return this;
    }

    public DynamicQueryBuilder andStaticWhere(String where) {
        super.andWhere(where);
        return this;
    }

    private DynamicQueryBuilder addWhereIn(String where) {
        String conditionName = getConditionName(where);
        if (conditionName != null && conditionName.startsWith(":")) {
            conditionName = conditionName.substring(1);
        }
        if (conditions.get(conditionName) == null || !conditions.containsKey(conditionName)) {
            return this;
        }

        String value = conditions.get(conditionName).toString();
        // categoryId IN ( :categoryIds )
        if (value != null && value.indexOf(",") > 0) {
            String[] arr = value.split(",");
            StringBuilder marks = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                String key = String.format("%s_%d", conditionName, i);
                // ":%s_%s" eg. :categoryIds_0, :categoryIds_1, :categoryIds_2
                marks.append(i > 0 ? ", :" : ":").append(key);
                conditions.put(key, arr[i]);
            }
            where = where.replace(":" + conditionName, marks);
        }

        super.andWhere(where);
        return this;
    }

    private boolean isInCondition(String where) {
        Pattern p = Pattern.compile("\\s+(IN)\\s+");
        Matcher m = p.matcher(where);
        return m.find();
    }

    private String getConditionName(String where) {
        Pattern p = Pattern.compile(":([a-zA-z0-9_]+)");
        Matcher m = p.matcher(where);
        return m.find() ? m.group() : null;
    }

    private boolean isWhereInConditions(String where) {
        String conditionName = getConditionName(where);
        if (conditionName == null) {
            return false;
        }

        if (conditionName.startsWith(":")) {
            conditionName = conditionName.substring(1);
        }

        return conditions.containsKey(conditionName) && conditions.get(conditionName) != null;
    }
}
