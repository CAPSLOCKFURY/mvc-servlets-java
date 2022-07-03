package sqlbuilder.clauses.general;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;

public class SubqueryClause implements SqlClause {

    private final SqlBuilder sqlBuilder;

    public SubqueryClause(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        visitor.exit(this);
    }

    public SqlBuilder getSqlBuilder() {
        return sqlBuilder;
    }
}
