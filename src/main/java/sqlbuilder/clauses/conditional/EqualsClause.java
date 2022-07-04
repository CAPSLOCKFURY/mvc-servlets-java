package sqlbuilder.clauses.conditional;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.general.SubqueryClause;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class EqualsClause<V> implements SqlClause {

    private final SqlField sqlField;

    private SubqueryClause subquery;

    private final V value;

    public EqualsClause(SqlField sqlField, V value) {
        this.sqlField = sqlField;
        this.value = value;
        if(value instanceof SqlBuilder){
            subquery = new SubqueryClause((SqlBuilder) value);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        if(hasSubquery()) {
            subquery.accept(visitor);
        }
        visitor.exit(this);
    }

    public SqlField getSqlField() {
        return sqlField;
    }

    public V getValue() {
        return value;
    }

    public SubqueryClause getSubquery() {
        return subquery;
    }

    public boolean hasSubquery(){
        return subquery != null;
    }
}
