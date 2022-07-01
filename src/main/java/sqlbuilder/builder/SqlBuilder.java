package sqlbuilder.builder;

import sqlbuilder.builder.base.AbstractSqlBuilder;
import sqlbuilder.builder.base.visitor.PostgreSQLVisitor;
import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.AndClause;
import sqlbuilder.clauses.conditional.OrClause;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public class SqlBuilder extends AbstractSqlBuilder {

    @Override
    public SqlBuilder select(SqlField ...sqlFields){
        sqlClauses.add(new SelectClause(sqlFields));
        return this;
    }

    @Override
    public SqlBuilder from(String tableName){
        sqlClauses.add(new FromClause(tableName));
        return this;
    }

    @Override
    public SqlBuilder from(SqlField sqlField){
        sqlClauses.add(new FromClause(sqlField.toString()));
        return this;
    }

    @Override
    public SqlBuilder from(SqlBuilder sqlBuilder){
        sqlClauses.add(new FromClause(null));
        sqlClauses.add(new SubqueryClause(sqlBuilder));
        return this;
    }

    @Override
    public SqlBuilder subquery(){
        return new SqlBuilder();
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias){
        return join(tableName, joinAlias, JoinType.INNER);
    }

    @Override
    public SqlBuilder join(SqlField tableName, String joinAlias, JoinType joinType){
        sqlClauses.add(new JoinClause(tableName, joinAlias, joinType));
        return this;
    }

    @Override
    public SqlBuilder join(SqlBuilder sqlBuilder){
        sqlClauses.add(new SubqueryClause(sqlBuilder));
        return this;
    }

    @Override
    public SqlBuilder on(SqlCondition sqlCondition){
        sqlClauses.add(new OnClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder where(SqlCondition sqlCondition){
        sqlClauses.add(new WhereClause(sqlCondition));
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
        sqlClauses.add(new AndClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder or(SqlCondition sqlCondition){
        sqlClauses.add(new OrClause(sqlCondition));
        return this;
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField){
        return orderBy(sqlField, SortDirection.ASC);
    }

    @Override
    public SqlBuilder orderBy(SqlField sqlField, SortDirection sortDirection){
        sqlClauses.add(new OrderByClause(sqlField, sortDirection));
        return this;
    }

    @Override
    public SqlBuilder groupBy(SqlField sqlField){
        sqlClauses.add(new GroupByClause(sqlField));
        return this;
    }

    @Override
    public String getSql(){
        return getSql(new PostgreSQLVisitor());
    }

    @Override
    public String getSql(Visitor visitor){
        for (SqlClause sqlClause : sqlClauses){
            sqlClause.accept(visitor);
        }
        return visitor.toSqlString();
    }

    @Override
    public String clear(){
        String returnCopy = getSql();
        sqlClauses.clear();
        return returnCopy;
    }

}
