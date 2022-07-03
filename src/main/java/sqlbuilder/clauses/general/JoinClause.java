package sqlbuilder.clauses.general;

import sqlbuilder.builder.base.JoinType;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class JoinClause implements SqlClause {

    private final SqlField tableName;

    private final String joinType;

    private final String joinAlias;

    public JoinClause(SqlField tableName, String joinAlias, JoinType joinType) {
        this.tableName = tableName;
        this.joinType = joinType.getJoinName();
        this.joinAlias = joinAlias;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public SqlField getTableName() {
        return tableName;
    }

    public String getJoinType() {
        return joinType;
    }

    public String getJoinAlias() {
        return joinAlias;
    }
}
