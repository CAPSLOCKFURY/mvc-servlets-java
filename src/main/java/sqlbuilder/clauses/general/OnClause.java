package sqlbuilder.clauses.general;

import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.clauses.base.SqlClause;

public class OnClause implements SqlClause {

    private final SqlCondition sqlCondition;

    public OnClause(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    @Override
    public String toSqlString() {
        return "on " + sqlCondition.getSql();
    }
}
