package sqlbuilder.clauses.conditional;

import sqlbuilder.clauses.base.SqlClause;

public class OrClause implements SqlClause {

    private final String expression;

    public OrClause(String expression) {
        this.expression = expression;
    }

    @Override
    public String toSqlString() {
        return "or " + expression;
    }
}
