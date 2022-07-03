package sqlbuilder.visitor.base;

import dao.factory.SqlDB;
import exceptions.sqlbuilder.NoVisitorForGivenDB;
import sqlbuilder.visitor.PostgreSQLVisitor;

public class VisitorFactory {

    public static Visitor getVisitor(SqlDB sqlDB){
        if(sqlDB == SqlDB.POSTGRESQL){
            return new PostgreSQLVisitor();
        }
        throw new NoVisitorForGivenDB("No visitor for given db type: " + sqlDB);
    }

}
