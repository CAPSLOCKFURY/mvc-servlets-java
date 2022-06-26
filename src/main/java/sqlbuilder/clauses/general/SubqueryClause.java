package sqlbuilder.clauses.general;

import sqlbuilder.clauses.base.SqlClause;

public class SubqueryClause implements SqlClause {

    private final String sql;

    public SubqueryClause(String sql) {
        this.sql = sql;
    }

    @Override
    public String toSqlString() {
        return "(" + sql + ")";
    }
}
