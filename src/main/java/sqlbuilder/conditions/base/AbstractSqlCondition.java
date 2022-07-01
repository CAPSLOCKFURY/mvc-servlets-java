package sqlbuilder.conditions.base;

import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.conditions.SqlCondition;

public abstract class AbstractSqlCondition {

    public abstract SqlCondition or(SqlCondition sqlCondition);

    public abstract SqlCondition and(SqlCondition sqlCondition);

//    public abstract String getSql();

    public abstract void accept(Visitor visitor);

}
