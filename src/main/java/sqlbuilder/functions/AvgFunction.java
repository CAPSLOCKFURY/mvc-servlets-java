package sqlbuilder.functions;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.functions.base.SqlFunction;
import sqlbuilder.model.SqlField;

public class AvgFunction implements SqlFunction {

    private final SqlField sqlField;

    public AvgFunction(SqlField sqlField) {
        this.sqlField = sqlField;
    }

    public SqlField getSqlField() {
        return sqlField;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
