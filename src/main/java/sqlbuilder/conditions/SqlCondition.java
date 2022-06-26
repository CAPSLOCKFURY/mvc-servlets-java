package sqlbuilder.conditions;

import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;

public class SqlCondition {

    private String sql;

    private boolean nestedCondition = false;

    public SqlCondition(String sql) {
        this.sql = sql;
    }

    public SqlCondition or(SqlCondition sqlCondition){
        nestedCondition = true;
        sql = sql.concat(new OrClause(sqlCondition.getSql()).toSqlString());
        return this;
    }

    public SqlCondition and(SqlCondition sqlCondition){
        nestedCondition = true;
        sql = sql.concat(new AndClause(sqlCondition.getSql()).toSqlString());
        return this;
    }

    public String getSql() {
        return nestedCondition ? "(" + sql + ")" : sql;
    }
}
