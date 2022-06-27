package sqlbuilder.builder;

import sqlbuilder.builder.base.AbstractSqlBuilder;
import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public class SqlBuilder extends AbstractSqlBuilder {

    public SqlBuilder(boolean isSubquery) {
        super(isSubquery);
    }

    public SqlBuilder() {

    }

    @Override
    public SqlBuilder select(SqlField ...sqlFields){
        appendSql(new SelectClause(sqlFields));
        return this;
    }

    @Override
    public SqlBuilder from(String tableName){
        appendSql(new FromClause(tableName));
        return this;
    }

    @Override
    public SqlBuilder from(SqlField sqlField){
        appendSql(new FromClause(sqlField.toString()));
        return this;
    }

    @Override
    public SqlBuilder from(SqlBuilder sqlBuilder){
        return from(sqlBuilder.getSql());
    }

    @Override
    public SqlBuilder subquery(){
        return new SqlBuilder(true);
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias){
        return join(tableName, joinAlias, JoinType.INNER);
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType){
        appendSql(new JoinClause(tableName, joinAlias, joinType));
        return this;
    }

    @Override
    public SqlBuilder join(SqlBuilder sqlBuilder){
        sql = sql.concat(sqlBuilder.getSql()) + " ";
        return this;
    }

    @Override
    public SqlBuilder on(SqlCondition sqlCondition){
        appendSql(new OnClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder where(SqlCondition sqlCondition){
        appendSql(new WhereClause(sqlCondition.getSql()));
        return this;
    }

    @Override
    public SqlBuilder where(SqlCondition ...sqlCondition){
        where(sqlCondition[0]);
        for (int i = 1; i < sqlCondition.length; i++) {
            and(sqlCondition[i]);
        }
        return this;
    }

    @Override
    public SqlBuilder and(SqlCondition sqlCondition){
        appendSql(new AndClause(sqlCondition.getSql()));
        return this;
    }

    @Override
    public SqlBuilder or(SqlCondition sqlCondition){
        appendSql(new OrClause(sqlCondition.getSql()));
        return this;
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField){
        return orderBy(sqlField, SortDirection.ASC);
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection){
        appendSql(new OrderByClause(sqlField, sortDirection));
        return this;
    }

    @Override
    public SqlBuilder groupBy(SqlField sqlField){
        appendSql(new GroupByClause(sqlField));
        return this;
    }

    @Override
    public String getSql(){
        return isSubquery ? new SubqueryClause(sql).toSqlString().trim() : sql.trim();
    }

    @Override
    public String clear(){
        String returnCopy = getSql();
        sql = "";
        return returnCopy;
    }

}
