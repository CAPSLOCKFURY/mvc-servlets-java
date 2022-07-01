package sqlbuilder.builder.base.visitor;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.*;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.functions.AvgFunction;
import sqlbuilder.functions.CountFunction;
import sqlbuilder.functions.SumFunction;
import sqlbuilder.functions.base.SqlFunction;
import sqlbuilder.model.SqlField;

public class PostgreSQLVisitor implements Visitor {

    private String sql = "";

    @Override
    public void visit(SelectClause clause) {
        SqlField[] sqlFields = clause.getSqlFields();
        for(SqlField sqlField : sqlFields){
            sqlField.accept(this);
        }
        String[] sqlFieldNames = new String[sqlFields.length];
        for (int i = 0; i < sqlFields.length; i++) {
            sqlFieldNames[i] = sqlFields[i].toString();
        }
        appendSql("select ".concat(String.join(", ", sqlFieldNames)));
    }

    @Override
    public void visit(FromClause clause) {
        if(clause.getTableName() == null){
            appendSql("from");
        } else {
            appendSql("from ".concat(clause.getTableName()));
        }
    }

    @Override
    public void visit(JoinClause clause) {
        appendSql(clause.getJoinAlias() == null || clause.getJoinAlias().equals("") ?
                clause.getJoinType() + " join " + clause.getTableName() :
                clause.getJoinType() + " join " + clause.getTableName() + " " + clause.getJoinAlias());
    }

    @Override
    public void visit(OnClause clause) {
        appendSql("on");
    }

    @Override
    public void visit(WhereClause clause) {
        appendSql("where");
    }

    @Override
    public void visit(AndClause clause) {
        appendSql("and");
    }

    @Override
    public void visit(OrClause clause) {
        appendSql("or");
    }

    @Override
    public void visit(OrderByClause clause) {
        appendSql("order by " + clause.getSqlField() + " " + clause.getSortDirection().toString().toLowerCase());
    }

    @Override
    public void visit(GroupByClause clause) {
        appendSql("group by " + clause.getSqlField());
    }

    @Override
    public void visit(SubqueryClause clause) {
        appendSql("(" + clause.getSqlBuilder().getSql() + " )");
    }

    @Override
    public void visit(SqlCondition condition) {
        if(condition.isNestedCondition()){
            sql += "(";
        }
        condition.getSqlField().accept(this);
        for (SqlClause sqlClause : condition.getClauses()) {
            sqlClause.accept(this);
        }
        if(condition.isNestedCondition()) {
            sql += ")";
        }
    }

    @Override
    public void visit(EqualsClause<?> clause) {
        String valueString;
        if (clause.getValue().getClass().equals(SqlBuilder.class)) {
            valueString = (((SqlBuilder) clause.getValue()).getSql());
        } else {
            valueString = clause.getValue().toString();
        }
        appendSql(clause.getSqlField().getFieldName() + " = " + valueString);
    }

    @Override
    public void visit(GreaterThanClause<?> clause) {
        appendSql(clause.getSqlField().getFieldName() + " > " + clause.getValue());
    }

    @Override
    public void visit(GreaterThanOrEqualsClause<?> clause) {
        appendSql(clause.getSqlField().getFieldName() + " >= " + clause.getValue());
    }

    @Override
    public void visit(LessThanClause<?> clause) {
        appendSql(clause.getSqlField().getFieldName() + " < " + clause.getValue());
    }

    @Override
    public void visit(LessThanOrEqualsClause<?> clause) {
        appendSql(clause.getSqlField().getFieldName() + " <= " + clause.getValue());
    }

    @Override
    public void visit(SqlField sqlField) {
        for(SqlFunction sqlFunction : sqlField.getSqlFunctions()){
            sqlFunction.accept(this);
        }
        if(sqlField.getAlias() != null){
            sqlField.setFieldName(sqlField.getFieldName() + " as " + sqlField.getAlias());
        }
    }

    @Override
    public void visit(SumFunction function) {
        SqlField sqlField = function.getSqlField();
        sqlField.setFieldName("sum(" + sqlField.getFieldName() + ")");
    }

    @Override
    public void visit(CountFunction function) {
        SqlField sqlField = function.getSqlField();
        sqlField.setFieldName("count(" + sqlField.getFieldName() + ")");
    }

    @Override
    public void visit(AvgFunction function) {
        SqlField sqlField = function.getSqlField();
        sqlField.setFieldName("avg(" + sqlField.getFieldName() + ")");
    }

    @Override
    public String toSqlString() {
        return sql.trim();
    }

    private void appendSql(String sql){
        this.sql = this.sql.concat(sql) + " ";
    }
}
