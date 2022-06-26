package sqlbuilder.clauses.general;

import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class GroupByClause implements SqlClause {

    private final SqlField sqlField;

    public GroupByClause(SqlField sqlField) {
        this.sqlField = sqlField;
    }

    @Override
    public String toSqlString() {
        return "group by " + sqlField;
    }
}
