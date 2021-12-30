package com.jetwinner.webfast.dao.support;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.dao.support.query.expression.CompositeExpression;
import com.jetwinner.webfast.dao.support.query.part.ConditionHolder;
import com.jetwinner.webfast.dao.support.query.part.FromAttribute;
import com.jetwinner.webfast.dao.support.query.part.JoinAttribute;
import com.jetwinner.webfast.dao.support.query.part.SqlPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x230-think-joomla
 * @date 2015/6/8
 */
public abstract class AbstractQueryBuilder {

    private static final String PDO_PARAM_STR = "s";
    private static final String PDO_PARAM_NUM = "n";
    private static final String PDO_PARAM_DATE = "d";

    /* The query types. */
    private static final int SELECT = 0;
    private static final int DELETE = 1;
    private static final int UPDATE = 2;

    /**
     * The builder states.
     */
    private static final int STATE_DIRTY = 0;
    private static final int STATE_CLEAN = 1;

    /**
     * @var array The array of SQL parts collected.
     */
    private SqlPart sqlParts = new SqlPart();

    /**
     * @var string The complete SQL string for this query.
     */
    private String sql;

    /**
     * @var array The query parameters.
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * @var array The parameter type map of this query.
     */
    private Map<String, Object> paramTypes = new HashMap<String, Object>();

    /**
     * @var integer The type of query this is. Can be select, update or delete.
     */
    private int type = SELECT;

    /**
     * @var integer The state of the query object. Can be dirty or clean.
     */
    private int state = STATE_CLEAN;

    /**
     * @var integer The index of the first result to retrieve.
     */
    private int firstResult = 0;

    /**
     * @var integer The maximum number of results to retrieve.
     */
    private int maxResults = 20;

    /**
     * The counter of bound parameters used with {@see bindValue)
     *
     * @var int
     */
    private int boundCounter = 0;

    /**
     * Gets an ExpressionBuilder used for object-oriented construction of query expressions.
     * This producer method is intended for convenient inline usage. Example:
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u")
     * ->from("users", "u")
     * ->where($qb->expr()->eq("u.id", 1));
     * </code>
     * <p/>
     * For more complex expression construction, consider storing the expression
     * builder object in a local variable.
     *
     * @return \Doctrine\DBAL\Query\Expression\ExpressionBuilder
     */
    public Object expr() {
        return null;
    }

    /**
     * Get the type of the currently built query.
     *
     * @return integer
     */
    public int getType() {
        return this.type;
    }

    /**
     * Get the state of this query builder instance.
     *
     * @return integer Either QueryBuilder::STATE_DIRTY or QueryBuilder::STATE_CLEAN.
     */
    public int getState() {
        return this.state;
    }

    /**
     * Get the complete SQL string formed by the current specifications of this QueryBuilder.
     * <p/>
     * <code>
     * $qb = $em->createQueryBuilder()
     * ->select("u")
     * ->from("User", "u")
     * echo $qb->getSQL(); // SELECT u FROM User u
     * </code>
     *
     * @return string The sql query string.
     */
    public String getSQL() {
        if (this.sql != null && this.state == STATE_CLEAN) {
            return this.sql;
        }

        sql = "";

        switch (this.type) {
            case DELETE:
                sql = this.getSQLForDelete();
                break;

            case UPDATE:
                sql = this.getSQLForUpdate();
                break;

            case SELECT:
            default:
                sql = this.getSQLForSelect();
                break;
        }

        this.state = STATE_CLEAN;
        this.sql = sql;

        return sql;
    }

    /**
     * Sets a query parameter for the query being constructed.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u")
     * ->from("users", "u")
     * ->where("u.id = :user_id")
     * ->setParameter(":user_id", 1);
     * </code>
     *
     * @param key   The parameter position or name.
     * @param value The parameter value.
     * @param type  PDO::PARAM_*
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder setParameter(String key, Object value, String type) {
        if (type != null) {
            this.paramTypes.put(key, type);
        }

        this.params.put(key, value);

        return this;
    }

    public AbstractQueryBuilder setParameter(String key, Object value) {
        return setParameter(key, value, null);
    }

    /**
     * Sets a collection of query parameters for the query being constructed.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u")
     * ->from("users", "u")
     * ->where("u.id = :user_id1 OR u.id = :user_id2")
     * ->setParameters(array(
     * ":user_id1" => 1,
     * ":user_id2" => 2
     * ));
     * </code>
     *
     * @param params The query parameters to set.
     * @param types  The query parameters types to set.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder setParameters(Map<String, Object> params, Map<String, Object> types) {
        this.paramTypes = types;
        this.params = params;

        return this;
    }

    /**
     * Gets all defined query parameters for the query being constructed.
     *
     * @return array The currently defined query parameters.
     */
    public Map<String, Object> getParameters() {
        return this.params;
    }

    /**
     * Gets a (previously set) query parameter of the query being constructed.
     *
     * @param key The key (index or name) of the bound parameter.
     * @return mixed The value of the bound parameter.
     */
    public Object getParameter(String key) {
        return this.params.get(key);
    }

    /**
     * Gets the position of the first result the query object was set to retrieve (the "offset").
     * Returns NULL if {@link #setFirstResult} was not applied to this QueryBuilder.
     *
     * @return integer The position of the first result.
     */
    public int getFirstResult() {
        return this.firstResult;
    }

    /**
     * Sets the position of the first result to retrieve (the "offset").
     *
     * @param firstResult The first result to return.
     * @return \Doctrine\DBAL\Query\QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder setFirstResult(int firstResult) {
        this.state = STATE_DIRTY;
        this.firstResult = firstResult;
        return this;
    }

    /**
     * Gets the maximum number of results the query object was set to retrieve (the "limit").
     * Returns NULL if {@link #setMaxResults} was not applied to this query builder.
     *
     * @return integer Maximum number of results.
     */
    public int getMaxResults() {
        return this.maxResults;
    }

    /**
     * Sets the maximum number of results to retrieve (the "limit").
     *
     * @param maxResults The maximum number of results to retrieve.
     * @return \Doctrine\DBAL\Query\QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder setMaxResults(int maxResults) {
        this.state = STATE_DIRTY;
        this.maxResults = maxResults;
        return this;
    }

    /**
     * Specifies an item that is to be returned in the query result.
     * Replaces any previously specified selections, if any.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.id", "p.id")
     * ->from("users", "u")
     * ->leftJoin("u", "phonenumbers", "p", "u.id = p.user_id");
     * </code>
     *
     * @param select The selection expressions.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder select(String... select) {
        this.type = SELECT;

        if (select == null || select.length < 1) {
            return this;
        }

        this.sqlParts.addSelect(select);

        return this;
    }

    /**
     * Adds an item that is to be returned in the query result.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.id")
     * ->addSelect("p.id")
     * ->from("users", "u")
     * ->leftJoin("u", "phonenumbers", "u.id = p.user_id");
     * </code>
     *
     * @param select The selection expression.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder addSelect(String... select) {
        this.type = SELECT;

        if (select == null || select.length < 1) {
            return this;
        }

        this.sqlParts.addSelect(select);

        return this;
    }

    /**
     * Turns the query being built into a bulk delete query that ranges over
     * a certain table.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->delete("users", "u")
     * ->where("u.id = :user_id");
     * ->setParameter(":user_id", 1);
     * </code>
     *
     * @param deleteTable The table whose rows are subject to the deletion.
     * @param alias       The table alias used in the constructed query.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder delete(String deleteTable, String alias) {
        this.type = DELETE;

        if (EasyStringUtil.isBlank(deleteTable)) {
            return this;
        }

        this.sqlParts.addFrom(new FromAttribute(deleteTable, alias));

        return this;
    }

    public AbstractQueryBuilder delete(String deleteTable) {
        return this.delete(deleteTable, null);
    }

    /**
     * Turns the query being built into a bulk update query that ranges over
     * a certain table
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->update("users", "u")
     * ->set("u.password", md5("password"))
     * ->where("u.id = ?");
     * </code>
     *
     * @param updateTable The table whose rows are subject to the update.
     * @param alias       The table alias used in the constructed query.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder update(String updateTable, String alias) {
        this.type = UPDATE;

        if (EasyStringUtil.isBlank(updateTable)) {
            return this;
        }

        this.sqlParts.addFrom(new FromAttribute(updateTable, alias));
        return this;
    }

    public AbstractQueryBuilder update(String updateTable) {
        return this.update(updateTable, null);
    }

    /**
     * Create and add a query root corresponding to the table identified by the
     * given alias, forming a cartesian product with any existing query roots.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.id")
     * ->from("users", "u")
     * </code>
     *
     * @param fromTable The table
     * @param alias     The alias of the table
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder from(String fromTable, String alias) {
        this.sqlParts.addFrom(new FromAttribute(fromTable, alias));
        return this;
    }

    public AbstractQueryBuilder from(String fromTable) {
        return from(fromTable, null);
    }

    /**
     * Creates and adds a join to the query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->join("u", "phonenumbers", "p", "p.is_primary = 1");
     * </code>
     *
     * @param fromAlias The alias that points to a from clause
     * @param join      The table name to join
     * @param alias     The alias of the join table
     * @param condition The condition for the join
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder join(String fromAlias, String join, String alias, String condition) {
        return this.innerJoin(fromAlias, join, alias, condition);
    }

    public AbstractQueryBuilder join(String fromAlias, String join, String alias) {
        return this.join(fromAlias, join, alias, null);
    }

    /**
     * Creates and adds a join to the query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->innerJoin("u", "phonenumbers", "p", "p.is_primary = 1");
     * </code>
     *
     * @param fromAlias The alias that points to a from clause
     * @param joinTable The table name to join
     * @param alias     The alias of the join table
     * @param condition The condition for the join
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder innerJoin(String fromAlias, String joinTable, String alias, String condition) {
        this.sqlParts.addJoin(fromAlias, new JoinAttribute("inner", joinTable, alias, condition));
        return this;
    }

    public AbstractQueryBuilder innerJoin(String fromAlias, String join, String alias) {
        return this.innerJoin(fromAlias, join, alias, null);
    }

    /**
     * Creates and adds a left join to the query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->leftJoin("u", "phonenumbers", "p", "p.is_primary = 1");
     * </code>
     *
     * @param fromAlias The alias that points to a from clause
     * @param joinTable The table name to join
     * @param alias     The alias of the join table
     * @param condition The condition for the join
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder leftJoin(String fromAlias, String joinTable, String alias, String condition) {
        this.sqlParts.addJoin(fromAlias, new JoinAttribute("left", joinTable, alias, condition));
        return this;
    }

    public AbstractQueryBuilder leftJoin(String fromAlias, String join, String alias) {
        return this.leftJoin(fromAlias, join, alias, null);
    }

    /**
     * Creates and adds a right join to the query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->rightJoin("u", "phonenumbers", "p", "p.is_primary = 1");
     * </code>
     *
     * @param fromAlias The alias that points to a from clause
     * @param joinTable The table name to join
     * @param alias     The alias of the join table
     * @param condition The condition for the join
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder rightJoin(String fromAlias, String joinTable, String alias, String condition) {
        this.sqlParts.addJoin(fromAlias, new JoinAttribute("right", joinTable, alias, condition));
        return this;

    }

    public AbstractQueryBuilder rightJoin(String fromAlias, String join, String alias) {
        return this.rightJoin(fromAlias, join, alias, null);
    }

    /**
     * Sets a new value for a column in a bulk update query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->update("users", "u")
     * ->set("u.password", md5("password"))
     * ->where("u.id = ?");
     * </code>
     *
     * @param key   The column to set.
     * @param value The value, expression, placeholder, etc.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder set(String key, String value) {
        this.sqlParts.addSet(key + " = " + value);
        return this;
    }

    /**
     * Specifies one or more restrictions to the query result.
     * Replaces any previously specified restrictions, if any.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->where("u.id = ?");
     * <p/>
     * // You can optionally programatically build and/or expressions
     * $qb = $conn->createQueryBuilder();
     * <p/>
     * $or = $qb->expr()->orx();
     * $or->add($qb->expr()->eq("u.id", 1));
     * $or->add($qb->expr()->eq("u.id", 2));
     * <p/>
     * $qb->update("users", "u")
     * ->set("u.password", md5("password"))
     * ->where($or);
     * </code>
     *
     * @param predicates The restriction predicates.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder where(Object... predicates) {
        if (!(predicates.length == 1 && predicates[0] instanceof CompositeExpression)) {
            this.sqlParts.addWhere(new CompositeExpression(CompositeExpression.TYPE_AND, predicates));
        } else {
            this.sqlParts.addWhere(predicates);
        }

        return this;
    }

    /**
     * Adds one or more restrictions to the query results, forming a logical
     * conjunction with any previously specified restrictions.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u")
     * ->from("users", "u")
     * ->where("u.username LIKE ?")
     * ->andWhere("u.is_active = 1");
     * </code>
     *
     * @param args The query restrictions.
     * @return QueryBuilder This QueryBuilder instance.
     * @see #where
     */
    public AbstractQueryBuilder andWhere(Object... args) {
        ConditionHolder where = this.sqlParts.getWhere();

        if (where.isExpression() && where.getExpression().getType() == CompositeExpression.TYPE_AND) {
            where.addMultiple(args);
        } else {
            List list = arrayUnshift(where.getList(), args);
            where.add(new CompositeExpression(CompositeExpression.TYPE_AND, list));
        }

        return this;
    }

    private List arrayUnshift(List list, Object[] args) {
        List newList = new ArrayList();
        if (args != null) {
            for (Object obj : args) {
                newList.add(obj);
            }
        }
        if (list != null && list.size() > 0) {
            newList.addAll(list);
        }
        return newList;
    }

    /**
     * Adds one or more restrictions to the query results, forming a logical
     * disjunction with any previously specified restrictions.
     * <p/>
     * <code>
     * $qb = $em->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->where("u.id = 1")
     * ->orWhere("u.id = 2");
     * </code>
     *
     * @param args The WHERE statement
     * @return QueryBuilder $qb
     * @see #where
     */
    public AbstractQueryBuilder orWhere(Object... args) {
        ConditionHolder where = this.sqlParts.getWhere();

        if (where.isExpression() && CompositeExpression.TYPE_OR.equals(where.getExpression().getType())) {
            where.addMultiple(args);
        } else {
            where.add(new CompositeExpression(CompositeExpression.TYPE_OR, where.addFirst(args).getList()));
        }

        return this;
    }

    /**
     * Specifies a grouping over the results of the query.
     * Replaces any previously specified groupings, if any.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->groupBy("u.id");
     * </code>
     *
     * @param groupBy The grouping expression.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder groupBy(String... groupBy) {
        if (groupBy == null || groupBy.length < 1) {
            return this;
        }

        this.sqlParts.addGroupBy(groupBy);

        return this;
    }


    /**
     * Adds a grouping expression to the query.
     * <p/>
     * <code>
     * $qb = $conn->createQueryBuilder()
     * ->select("u.name")
     * ->from("users", "u")
     * ->groupBy("u.lastLogin");
     * ->addGroupBy("u.createdAt")
     * </code>
     *
     * @param groupBy The grouping expression.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder addGroupBy(String... groupBy) {
        if (groupBy == null || groupBy.length < 1) {
            return this;
        }

        this.sqlParts.addGroupBy(groupBy);

        return this;
    }

    /**
     * Specifies a restriction over the groups of the query.
     * Replaces any previous having restrictions, if any.
     *
     * @param having The restriction over the groups.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder having(Object... having) {
        if (!(having != null && having[0] instanceof CompositeExpression)) {
            this.sqlParts.addHaving(new CompositeExpression(CompositeExpression.TYPE_AND, having));
        }

        return this;
    }

    /**
     * Adds a restriction over the groups of the query, forming a logical
     * conjunction with any existing having restrictions.
     *
     * @param args The restriction to append.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder andHaving(Object... args) {
        ConditionHolder having = this.sqlParts.getHaving();

        if (having.isExpression() && having.getExpression().getType() == CompositeExpression.TYPE_AND) {
            having.addMultiple(args);
        } else {
            having.add(new CompositeExpression(CompositeExpression.TYPE_AND, having.addFirst(args).getList()));
        }

        return this;
    }

    /**
     * Adds a restriction over the groups of the query, forming a logical
     * disjunction with any existing having restrictions.
     *
     * @param args The restriction to add.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder orHaving(Object... args) {
        ConditionHolder having = this.sqlParts.getHaving();

        if (having.isExpression() && having.getExpression().getType() == CompositeExpression.TYPE_OR) {
            having.addMultiple(args);
        } else {
            having.add(new CompositeExpression(CompositeExpression.TYPE_OR, having.addFirst(args).getList()));
        }

        return this;
    }

    /**
     * Specifies an ordering for the query results.
     * Replaces any previously specified orderings, if any.
     *
     * @param sort  The ordering expression.
     * @param order The ordering direction.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder orderBy(String sort, String order) {
        this.sqlParts.addOrderBy(sort + " " + (EasyStringUtil.isBlank(order) ? "ASC" : order));
        return this;
    }

    /**
     * Adds an ordering to the query results.
     *
     * @param sort  The ordering expression.
     * @param order The ordering direction.
     * @return QueryBuilder This QueryBuilder instance.
     */
    public AbstractQueryBuilder addOrderBy(String sort, String order) {
        this.sqlParts.addOrderBy(sort + " " + (EasyStringUtil.isBlank(order) ? "ASC" : order));
        return this;
    }

    public AbstractQueryBuilder addOrderBy(String sort) {
        return this.addOrderBy(sort, null);
    }

    /**
     * Reset single SQL part
     *
     * @param queryPartName
     * @return QueryBuilder
     */
    public AbstractQueryBuilder resetQueryPart(String queryPartName) {
        this.sqlParts.reset(queryPartName);

        this.state = STATE_DIRTY;

        return this;
    }

    private String getSQLForSelect() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ").append(EasyStringUtil.implode(", ", this.sqlParts.getSelect())).append(" FROM ");

        Map<String, String> fromClauses = new HashMap<String, String>();
        boolean joinsPending = true;
        Map<String, String> joinAliases = new HashMap<String, String>();


        // Loop through all FROM clauses
        for (FromAttribute from : this.sqlParts.getFrom()) {

            StringBuilder fromClause = new StringBuilder().append(from.getTable()).append(" ").append(from.getAlias());

            if (joinsPending && this.sqlParts.getJoin().containsKey(from.getAlias())) {
                for (JoinAttribute join : this.sqlParts.getJoin().values()) {
                    fromClause.append(" ").append(join.getJoinType())
                            .append(" JOIN ").append(join.getJoinTable()).append(" ").append(join.getJoinAlias())
                            .append(" ON ").append(join.getJoinCondition());

                    joinAliases.put(join.getJoinAlias(), "true");
                }
                joinsPending = false;
            }

            fromClauses.put(from.getAlias(), fromClause.toString());
        }

        // loop through all JOIN clauses for validation purpose
        Map<String, String> knownAliases = new HashMap<String, String>();
        knownAliases.putAll(joinAliases);
        knownAliases.putAll(fromClauses);
        for (JoinAttribute join : this.sqlParts.getJoin().values()) {
            if (!knownAliases.containsKey(join.getJoinAlias())) {
                return "";
            }
        }

        query.append(EasyStringUtil.implode(", ", fromClauses.values()));
        if (this.sqlParts.getWhere().isExpression()) {
            query.append(" WHERE ").append(this.sqlParts.getWhere());
        }

        if (this.sqlParts.hasGroupBy()) {
            query.append(" GROUP BY ").append(EasyStringUtil.implode(", ", this.sqlParts.getGroupBy()));
        }
        if (this.sqlParts.getHaving().isExpression()) {
            query.append(" HAVING ").append(this.sqlParts.getHaving());
        }
        if (this.sqlParts.hasOrderBy()) {
            query.append(" ORDER BY ").append(EasyStringUtil.implode(", ", this.sqlParts.getOrderBy()));
        }

        if (this.maxResults > 0 && this.firstResult >= 0) {
            query.append(" LIMIT ").append(this.maxResults).append(" OFFSET ").append(this.firstResult);
        }

        return query.toString();
    }

    /**
     * Converts this instance into an UPDATE string in SQL.
     *
     * @return string
     */
    private String getSQLForUpdate() {

        FromAttribute fromTable = this.sqlParts.getFrom().get(0);
        StringBuilder table = new StringBuilder().append(fromTable.getTable());
        if (EasyStringUtil.isNotBlank(fromTable.getAlias())) {
            table.append(" ").append(fromTable.getAlias());
        }
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(table).append(" SET ").append(EasyStringUtil.implode(", ", this.sqlParts.getSet()));
        if (sqlParts.getWhere().isExpression()) {
            query.append(" WHERE ").append(sqlParts.getWhere());
        }

        return query.toString();
    }

    /**
     * Converts this instance into a DELETE string in SQL.
     *
     * @return string
     */
    private String getSQLForDelete() {
        FromAttribute fromTable = this.sqlParts.getFrom().get(0);
        StringBuilder table = new StringBuilder().append(fromTable.getTable());
        if (EasyStringUtil.isNotBlank(fromTable.getAlias())) {
            table.append(" ").append(fromTable.getAlias());
        }
        StringBuilder query = new StringBuilder().append("DELETE FROM ").append(table);
        if (sqlParts.getWhere().isExpression()) {
            query.append(" WHERE ").append(sqlParts.getWhere());
        }

        return query.toString();
    }


    /**
     * Create a new named parameter and bind the value $value to it.
     * <p/>
     * This method provides a shortcut for PDOStatement::bindValue
     * when using prepared statements.
     * <p/>
     * The parameter $value specifies the value that you want to bind. If
     * $placeholder is not provided bindValue() will automatically create a
     * placeholder for you. An automatic placeholder will be of the name
     * ":dcValue1", ":dcValue2" etc.
     * <p/>
     * For more information see {@see #http://php.net/pdostatement-bindparam}
     * <p/>
     * Example:
     * <code>
     * $value = 2;
     * $q->eq( "id", $q->bindValue( $value ) );
     * $stmt = $q->executeQuery(); // executed with "id = 2"
     * </code>
     *
     * @param value
     * @param type
     * @param placeHolder the name to bind with. The string must start with a colon ":".
     * @return string the placeholder name used.
     * @license New BSD License
     * @link http://www.zetacomponents.org
     */
    public String createNamedParameter(Object value, String type, String placeHolder) {
        if (type == null) {
            type = PDO_PARAM_STR;
        }
        if (placeHolder == null) {
            this.boundCounter++;
            placeHolder = ":dcValue" + this.boundCounter;
        }
        this.setParameter(placeHolder.substring(1), value, type);

        return placeHolder;
    }

    /**
     * Create a new positional parameter and bind the given value to it.
     * <p/>
     * Attention: If you are using positional parameters with the query builder you have
     * to be very careful to bind all parameters in the order they appear in the SQL
     * statement , otherwise they get bound in the wrong order which can lead to serious
     * bugs in your code.
     * <p/>
     * Example:
     * <code>
     * $qb = $conn->createQueryBuilder();
     * $qb->select("u.*")
     * ->from("users", "u")
     * ->where("u.username = " . $qb->createPositionalParameter("Foo", PDO::PARAM_STR))
     * ->orWhere("u.username = " . $qb->createPositionalParameter("Bar", PDO::PARAM_STR))
     * </code>
     *
     * @param value
     * @param type
     * @return string
     */
    public String createPositionalParameter(Object value, String type) {
        if (type == null) {
            type = PDO_PARAM_STR;
        }
        this.boundCounter++;
        this.setParameter(String.valueOf(this.boundCounter), value, type);
        return "?";
    }
}
