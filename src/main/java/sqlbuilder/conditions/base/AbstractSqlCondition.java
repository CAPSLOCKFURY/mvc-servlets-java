package sqlbuilder.conditions.base;

import sqlbuilder.conditions.SqlCondition;

public abstract class AbstractSqlCondition {

    protected String sql;

    public AbstractSqlCondition(String sql) {
        this.sql = sql;
    }

    public abstract SqlCondition or(SqlCondition sqlCondition);

    public abstract SqlCondition and(SqlCondition sqlCondition);

    public abstract String getSql();

}
