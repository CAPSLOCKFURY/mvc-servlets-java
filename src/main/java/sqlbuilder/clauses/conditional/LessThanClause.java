package sqlbuilder.clauses.conditional;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class LessThanClause<V> implements SqlClause {

    private final SqlField sqlField;

    private final V value;

    public LessThanClause(SqlField sqlField, V value) {
        this.value = value;
        this.sqlField = sqlField;
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
