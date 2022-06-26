package sqlbuilder.clauses.conditional;

import sqlbuilder.clauses.base.SqlClause;

public class AndClause implements SqlClause {

    private final String expression;

    public AndClause(String expression) {
        this.expression = expression;
    }

    @Override
    public String toSqlString() {
        return "and " + expression;
    }
}
