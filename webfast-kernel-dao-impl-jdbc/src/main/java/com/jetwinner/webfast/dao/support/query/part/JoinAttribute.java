package com.jetwinner.webfast.dao.support.query.part;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/9
 */
public class JoinAttribute {

    private String joinType;
    private String joinTable;
    private String joinAlias;
    private String joinCondition;

    public JoinAttribute(String joinType, String joinTable, String joinAlias, String joinCondition) {
        this.joinType = joinType;
        this.joinTable = joinTable;
        this.joinAlias = joinAlias;
        this.joinCondition = joinCondition;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public void setJoinTable(String joinTable) {
        this.joinTable = joinTable;
    }

    public String getJoinAlias() {
        return joinAlias;
    }

    public void setJoinAlias(String joinAlias) {
        this.joinAlias = joinAlias;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }
}
