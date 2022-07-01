package sqlbuilder.model.base;

import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.builder.base.visitor.Visitor;
import sqlbuilder.conditions.SqlCondition;
import sqlbuilder.model.SqlField;

public abstract class AbstractSqlField {

    protected String fieldName;

    protected String sql = "";

    public AbstractSqlField(String fieldName) {
        this.fieldName = fieldName;
    }

    public abstract SqlField as(String alias);

    public abstract <V> SqlCondition eq(V value);

    public abstract SqlCondition eq(SqlBuilder sqlBuilder);

    public abstract <V> SqlCondition lt(V value);

    public abstract <V> SqlCondition gt(V value);

    public abstract <V> SqlCondition lte(V value);

    public abstract <V> SqlCondition gte(V value);

    public abstract SqlField sum();

    public abstract SqlField count();

    public abstract SqlField avg();

    public abstract void accept(Visitor visitor);

//    public void appendSql(SqlClause clause){
//        sql = sql.concat(clause.toSqlString());
//    }

}
