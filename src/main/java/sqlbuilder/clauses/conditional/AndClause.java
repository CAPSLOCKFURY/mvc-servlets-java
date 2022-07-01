package sqlbuilder.clauses.conditional;

import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;

public class AndClause implements SqlClause {

    private final SqlCondition sqlCondition;

    public AndClause(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        sqlCondition.accept(visitor);
    }

}
