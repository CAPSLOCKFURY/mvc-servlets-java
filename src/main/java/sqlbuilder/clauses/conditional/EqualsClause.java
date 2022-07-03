package sqlbuilder.clauses.conditional;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class EqualsClause<V> implements SqlClause {

    private final SqlField sqlField;

    private final V value;

    public EqualsClause(SqlField sqlField, V value) {
        this.sqlField = sqlField;
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        visitor.exit(this);
    }

    public SqlField getSqlField() {
        return sqlField;
    }

    public V getValue() {
        return value;
    }
}
