package sqlbuilder.clauses.general;

import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;

public class FromClause implements SqlClause {

    private final String tableName;

    public FromClause(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        visitor.exit(this);
    }

    public String getTableName() {
        return tableName;
    }
}
