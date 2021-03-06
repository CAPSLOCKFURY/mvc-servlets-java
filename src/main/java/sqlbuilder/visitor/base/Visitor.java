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

/**
 * Visitor class for constructing sql query from sql ast
 * visit method is called on enter in ast node
 * exit method is called at the end
 * <p>
 *     Note: see accept method in each node to understand how visitor methods will be called
 * </p>
 */
public interface Visitor {

    /**
     * Return sql string from given ast
     * <p>
     *     Note: you should iterate though ast and call accept methods
     * </p>
     * @param ast List of sql nodes
     * @return Constructed sql string
     */
    String toSqlString(List<SqlClause> ast);

    /**
     * Get sql string constructed from {@link #toSqlString} method call, and clears internal state of visitor if needed
     * @return Constructed sql
     */
    String clear();

    void visit(SelectClause clause);

    default void exit(SelectClause clause){}

    void visit(FromClause clause);

    default void exit(FromClause clause){}

    void visit(JoinClause clause);

    default void exit(JoinClause clause){}

    void visit(OnClause clause);

    default void exit(OnClause clause){}

    void visit(WhereClause clause);

    default void exit(WhereClause clause){}

    void visit(AndClause clause);

    default void exit(AndClause clause){}

    void visit(OrClause clause);

    default void exit(OrClause clause){}

    void visit(OrderByClause clause);

    default void exit(OrderByClause clause){}

    void visit(GroupByClause clause);

    default void exit(GroupByClause clause){}

    void visit(SubqueryClause clause);

    default void exit(SubqueryClause clause){}

    void visit(SqlCondition condition);

    default void exit(SqlCondition condition){}

    void visit(EqualsClause<?> clause);

    default void exit(EqualsClause<?> clause){}

    void visit(GreaterThanClause<?> clause);

    default void exit(GreaterThanClause<?> clause){}

    void visit(GreaterThanOrEqualsClause<?> clause);

    default void exit(GreaterThanOrEqualsClause<?> clause){}

    void visit(LessThanClause<?> clause);

    default void exit(LessThanClause<?> clause){}

    void visit(LessThanOrEqualsClause<?> clause);

    default void exit(LessThanOrEqualsClause<?> clause){}

    void visit(SqlField sqlField);

    default void exit(SqlField sqlField){}

    void visit(SumFunction function);

    default void exit(SumFunction function){}

    void visit(CountFunction function);

    default void exit(CountFunction function){}

    void visit(AvgFunction function);

    default void exit(AvgFunction function){}

}
