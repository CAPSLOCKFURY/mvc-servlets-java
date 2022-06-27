package sqlbuilder.builder.base;

import sqlbuilder.builder.JoinType;
import sqlbuilder.builder.SortDirection;
import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public abstract class AbstractSqlBuilder {

    protected String sql = "";

    protected boolean isSubquery = false;

    public AbstractSqlBuilder(boolean isSubquery) {
        this.isSubquery = isSubquery;
    }

    public AbstractSqlBuilder() {

    }

    public abstract SqlBuilder select(SqlField...sqlFields);

    public abstract SqlBuilder from(String tableName);

    public abstract SqlBuilder from(SqlField sqlField);

    public abstract SqlBuilder from(SqlBuilder sqlBuilder);

    public abstract SqlBuilder subquery();

    public abstract SqlBuilder join(SqlField tableName, String joinAlias);

    public abstract SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType);

    public abstract SqlBuilder join(SqlBuilder sqlBuilder);

    public abstract SqlBuilder on(SqlCondition sqlCondition);

    public abstract SqlBuilder where(SqlCondition sqlCondition);

    public abstract SqlBuilder where(SqlCondition ...sqlCondition);

    public abstract SqlBuilder and(SqlCondition sqlCondition);

    public abstract SqlBuilder or(SqlCondition sqlCondition);

    public abstract SqlBuilder orderBy(SqlField sqlField);

    public abstract SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection);

    public abstract SqlBuilder groupBy(SqlField sqlField);

    public abstract String getSql();

    public void appendSql(SqlClause clause){
        sql = sql.concat(clause.toSqlString()) + " ";
    }

    public abstract String clear();
}
