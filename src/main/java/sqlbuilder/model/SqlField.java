package sqlbuilder.model;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.visitor.base.Visitor;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.functions.AvgFunction;
import sqlbuilder.functions.CountFunction;
import sqlbuilder.functions.SumFunction;
import sqlbuilder.functions.base.SqlFunction;
import sqlbuilder.model.base.AbstractSqlField;

import java.util.LinkedList;
import java.util.List;

public class SqlField extends AbstractSqlField {

    private SqlClause sqlClause;

    private String alias;

    private final List<SqlFunction> sqlFunctions = new LinkedList<>();

    public SqlField(String fieldName) {
        super(fieldName);
    }

    @Override
    public SqlField as(String alias){
        this.alias = alias;
        return this;
    }

    @Override
    public <V> SqlCondition eq(V value){
        sqlClause = new EqualsClause<>(this, value);
        return new SqlCondition(this);
    }

    @Override
    public SqlCondition eq(SqlBuilder sqlBuilder){
        sqlClause = new EqualsClause<>(this, sqlBuilder);
        return new SqlCondition(this);
    }

    @Override
    public <V> SqlCondition lt(V value){
        sqlClause = new LessThanClause<>(this, value);
        return new SqlCondition(this);
    }

    @Override
    public <V> SqlCondition gt(V value){
        sqlClause = new GreaterThanClause<>(this, value);
        return new SqlCondition(this);
    }

    @Override
    public <V> SqlCondition lte(V value){
        sqlClause = new LessThanOrEqualsClause<>(this, value);
        return new SqlCondition(this);
    }

    @Override
    public <V> SqlCondition gte(V value){
        sqlClause = new GreaterThanOrEqualsClause<>(this, value);
        return new SqlCondition(this);
    }

    @Override
    public SqlField sum(){
        sqlFunctions.add(new SumFunction(this));
        return this;
    }

    @Override
    public SqlField count(){
        sqlFunctions.add(new CountFunction(this));
        return this;
    }

    @Override
    public SqlField avg(){
        sqlFunctions.add(new AvgFunction(this));
        return this;
    }

    @Override
    public String toString() {
        return fieldName;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for(SqlFunction sqlFunction : sqlFunctions) {
            sqlFunction.accept(visitor);
        }
        if(sqlClause != null) {
            sqlClause.accept(visitor);
        }
        visitor.exit(this);
    }

    public SqlClause getSqlClause() {
        return sqlClause;
    }

    public String getAlias() {
        return alias;
    }

    public String getFieldName(){
        return fieldName;
    }

    public List<SqlFunction> getSqlFunctions() {
        return sqlFunctions;
    }

    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }
}
