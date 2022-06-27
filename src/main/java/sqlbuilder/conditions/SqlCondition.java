package sqlbuilder.conditions;

import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.conditions.base.AbstractSqlCondition;

public class SqlCondition extends AbstractSqlCondition {

    private boolean nestedCondition = false;

    public SqlCondition(String sql) {
        super(sql);
    }

    @Override
    public SqlCondition or(SqlCondition sqlCondition){
        nestedCondition = true;
        sql = sql.concat(" " + new OrClause(sqlCondition.getSql()).toSqlString());
        return this;
    }

    @Override
    public SqlCondition and(SqlCondition sqlCondition){
        nestedCondition = true;
        sql = sql.concat(" " + new AndClause(sqlCondition.getSql()).toSqlString());
        return this;
    }

    @Override
    public String getSql() {
        return nestedCondition ? "(" + sql + ")" : sql;
    }
}
