package sqlbuilder.clauses.general;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class SelectClause implements SqlClause {

    private final SqlField[] sqlFields;

    public SelectClause(SqlField[] sqlFields) {
        this.sqlFields = sqlFields;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for(SqlField sqlField : sqlFields){
            sqlField.accept(visitor);
        }
        visitor.exit(this);
    }

    public SqlField[] getSqlFields() {
        return sqlFields;
    }
}
