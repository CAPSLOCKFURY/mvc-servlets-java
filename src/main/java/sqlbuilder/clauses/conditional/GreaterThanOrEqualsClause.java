package sqlbuilder.clauses.conditional;

import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class GreaterThanOrEqualsClause<V> implements SqlClause {

    private final SqlField sqlField;

    private final V value;

    public GreaterThanOrEqualsClause(SqlField sqlField, V value) {
        this.sqlField = sqlField;
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public V getValue() {
        return value;
    }

    public SqlField getSqlField() {
        return sqlField;
    }
}
