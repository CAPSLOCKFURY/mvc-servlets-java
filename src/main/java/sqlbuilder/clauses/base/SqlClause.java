package sqlbuilder.clauses.base;

import sqlbuilder.visitor.base.Visitor;

public interface SqlClause {

    void accept(Visitor visitor);

}
