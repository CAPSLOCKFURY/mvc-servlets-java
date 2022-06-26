package sqlbuilder.clauses.conditional;

import sqlbuilder.clauses.base.SqlClause;

public class LessThanOrEqualsClause<V> implements SqlClause {

    private final V value;

    private final String fieldName;

    public LessThanOrEqualsClause(String fieldName, V value) {
        this.value = value;
        this.fieldName = fieldName;
    }

    @Override
    public String toSqlString() {
        return fieldName + " <= " + value;
    }
}
