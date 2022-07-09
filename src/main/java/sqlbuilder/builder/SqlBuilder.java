package sqlbuilder.builder;

import context.ApplicationContext;
import context.ContextHolder;
import dao.factory.SqlDB;
import sqlbuilder.builder.base.JoinType;
import sqlbuilder.builder.base.SortDirection;
import sqlbuilder.builder.base.AbstractSqlBuilder;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public class SqlBuilder extends AbstractSqlBuilder {

    private static final ApplicationContext context = ContextHolder.getInstance().getApplicationContext();

    public SqlBuilder() {
        super(context.sqlDb());
    }

    public SqlBuilder(SqlDB sqlDB) {
        super(sqlDB);
    }

    private SqlBuilder(Visitor visitor){
        super(visitor);
    }

    @Override
    public SqlBuilder select(SqlField ...sqlFields){
        ast.add(new SelectClause(sqlFields));
        return this;
    }

    @Override
    public SqlBuilder from(String tableName){
        ast.add(new FromClause(tableName));
        return this;
    }

    @Override
    public SqlBuilder from(SqlField sqlField){
        ast.add(new FromClause(sqlField.toString()));
        return this;
    }

    @Override
    public SqlBuilder from(SqlBuilder sqlBuilder){
        ast.add(new FromClause(null));
        ast.add(new SubqueryClause(sqlBuilder));
        return this;
    }

    @Override
    public SqlBuilder subquery(){
        return new SqlBuilder(visitor);
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias){
        return join(tableName, joinAlias, JoinType.INNER);
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType){
        ast.add(new JoinClause(tableName, joinAlias, joinType));
        return this;
    }

    @Override
    public SqlBuilder join(SqlBuilder sqlBuilder){
        ast.add(new SubqueryClause(sqlBuilder));
        return this;
    }

    @Override
    public SqlBuilder on(SqlCondition sqlCondition){
        ast.add(new OnClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder where(SqlCondition sqlCondition){
        ast.add(new WhereClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder where(SqlCondition ...sqlCondition){
        where(sqlCondition[0]);
        for (int i = 1; i < sqlCondition.length; i++) {
            and(sqlCondition[i]);
        }
        return this;
    }

    @Override
    public SqlBuilder and(SqlCondition sqlCondition){
        ast.add(new AndClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder or(SqlCondition sqlCondition){
        ast.add(new OrClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField){
        return orderBy(sqlField, SortDirection.ASC);
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection){
        ast.add(new OrderByClause(sqlField, sortDirection));
        return this;
    }

    @Override
    public SqlBuilder groupBy(SqlField sqlField){
        ast.add(new GroupByClause(sqlField));
        return this;
    }

    @Override
    public String getSql(){
        return visitor.toSqlString(ast);
    }

    @Override
    public String clear(){
        String returnCopy = getSql();
        ast.clear();
        visitor.clear();
        return returnCopy;
    }

}
