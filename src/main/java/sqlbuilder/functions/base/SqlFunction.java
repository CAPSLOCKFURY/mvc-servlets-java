package sqlbuilder.functions.base;

import sqlbuilder.visitor.base.Visitor;

public interface SqlFunction {

    void accept(Visitor visitor);

}
