package sqlbuilder.clauses.general;

import sqlbuilder.builder.JoinType;
import sqlbuilder.model.SqlField;
import sqlbuilder.clauses.base.SqlClause;

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
    public String toSqlString() {
        return joinAlias == null ? joinType + " join " + tableName : joinType + " join " + tableName + " " + joinAlias;
    }
}
