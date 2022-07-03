package sqlbuilder.visitor.base;

import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.clauses.conditional.*;
import sqlbuilder.clauses.general.*;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.functions.AvgFunction;
import sqlbuilder.functions.CountFunction;
import sqlbuilder.functions.SumFunction;
import sqlbuilder.model.SqlField;

import java.util.List;

public interface Visitor {

    String toSqlString(List<SqlClause> ast);

    String clear();

    void visit(SelectClause clause);

    void visit(FromClause clause);

    void visit(JoinClause clause);

    void visit(OnClause clause);

    void visit(WhereClause clause);

    void visit(AndClause clause);

    void visit(OrClause clause);

    void visit(OrderByClause clause);

    void visit(GroupByClause clause);

    void visit(SubqueryClause clause);

    void visit(SqlCondition condition);

    void visit(EqualsClause<?> clause);

    void visit(GreaterThanClause<?> clause);

    void visit(GreaterThanOrEqualsClause<?> clause);

    void visit(LessThanClause<?> clause);

    void visit(LessThanOrEqualsClause<?> clause);

    void visit(SqlField sqlField);

    void visit(SumFunction function);

    void visit(CountFunction function);

    void visit(AvgFunction function);

}
