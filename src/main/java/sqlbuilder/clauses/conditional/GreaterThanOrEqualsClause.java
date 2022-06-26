package sqlbuilder.clauses.conditional;

import sqlbuilder.clauses.base.SqlClause;

public class GreaterThanOrEqualsClause<V> implements SqlClause {

    private final V value;

    private final String fieldName;

    public GreaterThanOrEqualsClause( String fieldName, V value) {
        this.value = value;
        this.fieldName = fieldName;
    }

    @Override
    public String toSqlString() {
        return fieldName + " >= " + value;
    }
}
