package sqlbuilder.clauses.conditional;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;

public class OrClause implements SqlClause {

    private final SqlCondition sqlCondition;

    public OrClause(SqlCondition sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        sqlCondition.accept(visitor);
        visitor.exit(this);
    }

}
