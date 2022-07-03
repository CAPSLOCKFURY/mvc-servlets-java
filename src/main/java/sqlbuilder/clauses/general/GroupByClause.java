package sqlbuilder.clauses.general;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class GroupByClause implements SqlClause {

    private final SqlField sqlField;

    public GroupByClause(SqlField sqlField) {
        this.sqlField = sqlField;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        visitor.exit(this);
    }

    public SqlField getSqlField() {
        return sqlField;
    }
}
