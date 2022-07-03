package sqlbuilder.clauses.general;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;

public class OnClause implements SqlClause {

    private final SqlCondition sqlCondition;

    public OnClause(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        sqlCondition.accept(visitor);
    }
}
