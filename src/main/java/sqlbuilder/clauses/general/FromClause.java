package sqlbuilder.clauses.general;

import sqlbuilder.clauses.base.SqlClause;

public class FromClause implements SqlClause {

    private final String tableName;

    public FromClause(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toSqlString() {
        return "from ".concat(tableName);
    }
}
