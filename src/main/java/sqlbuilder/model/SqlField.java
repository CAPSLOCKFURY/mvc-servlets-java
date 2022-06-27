package sqlbuilder.model;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.base.AbstractSqlField;

public class SqlField extends AbstractSqlField {

    public SqlField(String fieldName) {
        super(fieldName);
    }

    @Override
    public SqlField as(String alias){
        fieldName = fieldName + " as " + alias;
        return this;
    }

    @Override
    public <V> SqlCondition eq(V value){
        appendSql(new EqualsClause<>(fieldName, value));
        return new SqlCondition(getSql());
    }

    @Override
    public SqlCondition eq(SqlBuilder sqlBuilder){
        appendSql(new EqualsClause<>(fieldName, sqlBuilder));
        return new SqlCondition(getSql());
    }

    @Override
    public <V> SqlCondition lt(V value){
        appendSql(new LessThanClause<>(fieldName, value));
        return new SqlCondition(getSql());
    }

    @Override
    public <V> SqlCondition gt(V value){
        appendSql(new GreaterThanClause<>(fieldName, value));
        return new SqlCondition(getSql());
    }

    @Override
    public <V> SqlCondition lte(V value){
        appendSql(new LessThanOrEqualsClause<>(fieldName, value));
        return new SqlCondition(getSql());
    }

    @Override
    public <V> SqlCondition gte(V value){
        appendSql(new GreaterThanOrEqualsClause<>(fieldName, value));
        return new SqlCondition(getSql());
    }

    @Override
    public SqlField sum(){
        fieldName = "sum(" + fieldName + ")";
        return this;
    }

    @Override
    public SqlField count(){
        fieldName = "count(" + fieldName + ")";
        return this;
    }

    @Override
    public SqlField avg(){
        fieldName = "avg(" + fieldName + ")";
        return this;
    }

    @Override
    public String toString() {
        return fieldName;
    }

    @Override
    public String getSql(){
        return sql;
    }

    public void appendSql(SqlClause clause){
        sql = sql.concat(clause.toSqlString());
    }

}
