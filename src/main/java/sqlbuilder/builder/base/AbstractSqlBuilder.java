package sqlbuilder.builder.base;

import dao.factory.SqlDB;
import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.visitor.PostgreSQLVisitor;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;
import sqlbuilder.visitor.base.VisitorFactory;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSqlBuilder {

    protected Visitor visitor;


    public AbstractSqlBuilder(SqlDB sqlDB) {
        this(VisitorFactory.getVisitor(sqlDB));
    }

    public AbstractSqlBuilder(Visitor visitor){
        this.visitor = visitor;
    }

    protected final List<SqlClause> ast = new LinkedList<>();

    public abstract SqlBuilder select(SqlField...sqlFields);

    public abstract SqlBuilder from(String tableName);

    public abstract SqlBuilder from(SqlField sqlField);

    public abstract SqlBuilder from(SqlBuilder sqlBuilder);

    public abstract SqlBuilder subquery();

    public abstract SqlBuilder join(SqlField tableName, String joinAlias);

    public abstract SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType);

    public abstract SqlBuilder join(SqlBuilder sqlBuilder);

    public abstract SqlBuilder on(SqlCondition sqlCondition);

    public abstract SqlBuilder where(SqlCondition sqlCondition);

    public abstract SqlBuilder where(SqlCondition ...sqlCondition);

    public abstract SqlBuilder and(SqlCondition sqlCondition);

    public abstract SqlBuilder or(SqlCondition sqlCondition);

    public abstract SqlBuilder orderBy(SqlField sqlField);

    public abstract SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection);

    public abstract SqlBuilder groupBy(SqlField sqlField);

    public abstract String getSql();

    public abstract String clear();

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public List<SqlClause> getAst() {
        return ast;
    }

    public Visitor getVisitor() {
        return visitor;
    }
}
