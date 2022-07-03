package sqlbuilder.clauses.general;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;

public class WhereClause implements SqlClause {

    private final SqlCondition sqlCondition;

    public WhereClause(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        sqlCondition.accept(visitor);
        visitor.exit(this);
    }
}
