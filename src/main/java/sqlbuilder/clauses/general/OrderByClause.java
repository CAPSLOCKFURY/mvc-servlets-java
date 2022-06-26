package sqlbuilder.clauses.general;

import sqlbuilder.builder.SortDirection;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class OrderByClause implements SqlClause {

    private final SqlField sqlField;

    private final SortDirection sortDirection;

    public OrderByClause(SqlField sqlField, SortDirection sortDirection) {
        this.sqlField = sqlField;
        this.sortDirection = sortDirection;
    }

    @Override
    public String toSqlString() {
        return "order by " + sqlField + " " + sortDirection.getSortDirection();
    }
}
