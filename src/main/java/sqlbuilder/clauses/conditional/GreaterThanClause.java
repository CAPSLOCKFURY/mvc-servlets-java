package sqlbuilder.clauses.conditional;

import sqlbuilder.clauses.base.SqlClause;

public class GreaterThanClause<V> implements SqlClause {

    private final V value;

    private final String fieldName;

    public GreaterThanClause(String fieldName, V value) {
        this.value = value;
        this.fieldName = fieldName;
    }

    @Override
    public String toSqlString() {
        return fieldName + " > " + value;
    }
}
