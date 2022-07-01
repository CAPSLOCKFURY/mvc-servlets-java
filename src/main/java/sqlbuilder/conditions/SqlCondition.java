package sqlbuilder.conditions;

import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.conditions.base.AbstractSqlCondition;
import sqlbuilder.model.SqlField;

import java.util.LinkedList;
import java.util.List;

public class SqlCondition extends AbstractSqlCondition {

    private final SqlField sqlField;

    private boolean nestedCondition = false;

    private final List<SqlClause> clauses = new LinkedList<>();

    public SqlCondition(SqlField sqlField) {
        this.sqlField = sqlField;
    }

    @Override
    public SqlCondition or(SqlCondition sqlCondition){
        nestedCondition = true;
        clauses.add(new OrClause(sqlCondition));
        return this;
    }

    @Override
    public SqlCondition and(SqlCondition sqlCondition){
        nestedCondition = true;
        clauses.add(new AndClause(sqlCondition));
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isNestedCondition() {
        return nestedCondition;
    }

    public List<SqlClause> getClauses() {
        return clauses;
    }

    public SqlField getSqlField() {
        return sqlField;
    }
}
