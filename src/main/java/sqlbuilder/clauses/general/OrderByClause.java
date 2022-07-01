package sqlbuilder.clauses.general;

import sqlbuilder.builder.SortDirection;
import sqlbuilder.builder.base.visitor.Visitor;
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
    public void accept(Visitor visitor) {
        sqlField.accept(visitor);
        visitor.visit(this);
    }

    public SqlField getSqlField() {
        return sqlField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }
}
