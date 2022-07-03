package sqlbuilder.functions;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.functions.base.SqlFunction;
import sqlbuilder.model.SqlField;

public class SumFunction implements SqlFunction {

    private final SqlField sqlField;

    public SumFunction(SqlField sqlField) {
        this.sqlField = sqlField;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public SqlField getSqlField() {
        return sqlField;
    }
}
