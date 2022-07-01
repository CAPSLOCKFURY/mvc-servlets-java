package sqlbuilder.functions.base;

import sqlbuilder.builder.base.visitor.Visitor;

public interface SqlFunction {

    void accept(Visitor visitor);

}
