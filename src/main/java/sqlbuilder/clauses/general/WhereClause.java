package sqlbuilder.clauses.general;

import sqlbuilder.clauses.base.SqlClause;

public class WhereClause implements SqlClause {

    private final String expression;

    public WhereClause(String expressions) {
        this.expression = expressions;
    }

    @Override
    public String toSqlString() {
        return "where ".concat(expression);
    }
}
