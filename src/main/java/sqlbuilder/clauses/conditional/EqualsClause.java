package sqlbuilder.clauses.conditional;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.base.SqlClause;

public class EqualsClause<V> implements SqlClause {

    private final String fieldName;

    private final V value;

    public EqualsClause(String fieldName, V value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    @Override
    public String toSqlString() {
        String valueString;
        if (value.getClass().equals(SqlBuilder.class)) {
            valueString = (((SqlBuilder) value).getSql());
        } else {
            valueString = value.toString();
        }
        return fieldName + " = " + valueString;
    }
}
