package sqlbuilder.builder;

import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public class SqlBuilder {

    private String sql = "";

    private boolean isSubquery = false;

    public SqlBuilder(boolean isSubquery) {
        this.isSubquery = isSubquery;
    }

    public SqlBuilder() {

    }

    public SqlBuilder select(SqlField ...sqlFields){
        appendSql(new SelectClause(sqlFields));
        return this;
    }

    public SqlBuilder from(String tableName){
        appendSql(new FromClause(tableName));
        return this;
    }

    public SqlBuilder from(SqlField sqlField){
        appendSql(new FromClause(sqlField.toString()));
        return this;
    }

    public SqlBuilder from(SqlBuilder sqlBuilder){
        return from(sqlBuilder.getSql());
    }

    public SqlBuilder subquery(){
        return new SqlBuilder(true);
    }

    public SqlBuilder join(SqlField tableName, String joinAlias){
        return join(tableName, joinAlias, JoinType.INNER);
    }

    public SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType){
        appendSql(new JoinClause(tableName, joinAlias, joinType));
        return this;
    }

    public SqlBuilder join(SqlBuilder sqlBuilder){
        sql = sql.concat(sqlBuilder.getSql()) + " ";
        return this;
    }

    public SqlBuilder on(SqlCondition sqlCondition){
        appendSql(new OnClause(sqlCondition));
        return this;
    }

    public SqlBuilder where(SqlCondition sqlCondition){
        appendSql(new WhereClause(sqlCondition.getSql()));
        return this;
    }

    public SqlBuilder where(SqlCondition ...sqlCondition){
        where(sqlCondition[0]);
        for (int i = 1; i < sqlCondition.length; i++) {
            and(sqlCondition[i]);
        }
        return this;
    }

    public SqlBuilder and(SqlCondition sqlCondition){
        appendSql(new AndClause(sqlCondition.getSql()));
        return this;
    }

    public SqlBuilder or(SqlCondition sqlCondition){
        appendSql(new OrClause(sqlCondition.getSql()));
        return this;
    }

    public SqlBuilder orderBy(SqlField sqlField){
        return orderBy(sqlField, SortDirection.ASC);
    }

    public SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection){
        appendSql(new OrderByClause(sqlField, sortDirection));
        return this;
    }

    public SqlBuilder groupBy(SqlField sqlField){
        appendSql(new GroupByClause(sqlField));
        return this;
    }

    public String getSql(){
        return isSubquery ? new SubqueryClause(sql).toSqlString().trim() : sql.trim();
    }

    public void appendSql(SqlClause clause){
        sql = sql.concat(clause.toSqlString()) + " ";
    }

    public String clear(){
        String returnCopy = getSql();
        sql = "";
        return returnCopy;
    }

}
