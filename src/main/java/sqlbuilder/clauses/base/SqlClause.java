package sqlbuilder.clauses.base;

import sqlbuilder.builder.base.visitor.Visitor;

public interface SqlClause {

    void accept(Visitor visitor);

}
