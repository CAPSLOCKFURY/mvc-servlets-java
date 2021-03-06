package sqlbuilder.visitor;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.*;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.functions.AvgFunction;
import sqlbuilder.functions.CountFunction;
import sqlbuilder.functions.SumFunction;
import sqlbuilder.model.SqlField;
import sqlbuilder.visitor.base.Visitor;

import java.util.List;

public class PostgreSQLVisitor implements Visitor {

    private StringBuilder sql = new StringBuilder();

    @Override
    public String toSqlString(List<SqlClause> ast) {
        for (SqlClause sqlClause : ast){
            sqlClause.accept(this);
        }
        return sql.toString().trim();
    }

    @Override
    public void visit(SelectClause clause) {
        appendSql("select");
    }

    @Override
    public void exit(SelectClause clause){
        SqlField[] sqlFields = clause.getSqlFields();
        String[] sqlFieldNames = new String[sqlFields.length];
        for (int i = 0; i < sqlFields.length; i++) {
            sqlFieldNames[i] = sqlFields[i].getFieldName();
        }
        appendSql(String.join(", ", sqlFieldNames));
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
        sql.append("(");
        toSqlString(clause.getSqlBuilder().getAst());
    }

    @Override
    public void exit(SubqueryClause clause){
        removeLastEmptyChar();
        appendSql(")");
    }

    @Override
    public void visit(SqlCondition condition) {
        if(condition.isNestedCondition()){
            sql.append("(");
        }
    }

    @Override
    public void exit(SqlCondition condition){
        if(condition.isNestedCondition()) {
            removeLastEmptyChar();
            appendSql(")");
        }
    }

    @Override
    public void visit(EqualsClause<?> clause) {
        if (clause.hasSubquery()) {
            appendSql(clause.getSqlField().getFieldName() + " =");
        } else {
            String valueString = clause.getValue().toString();
            appendSql(clause.getSqlField().getFieldName() + " = " + valueString);
        }
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

    }

    @Override
    public void exit(SqlField sqlField){
        if(sqlField.getAlias() != null){
            sqlField.setFieldName(sqlField.getFieldName() + " as " + sqlField.getAlias());
        }
        if(sqlField.isNegated()){
            sqlField.setFieldName("not ".concat(sqlField.getFieldName()));
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
    public String clear() {
        String sqlCopy = sql.toString();
        sql.setLength(0);
        return sqlCopy;
    }

    private void appendSql(String sql){
        this.sql.append(sql.concat(" "));
    }

    private char lastChar(){
        return sql.charAt(sql.length() - 1);
    }

    private void removeLastChar(){
        sql.setLength(sql.length() - 1);
    }

    private void removeLastEmptyChar(){
        if(lastChar() == ' '){
            removeLastChar();
        }
    }
}
