package dao.factory;

import dao.factory.impl.PostgreSQLDaoFactory;

public class DaoAbstractFactory {

    public static DaoFactory getFactory(SqlDB type){
        if(type == SqlDB.POSTGRESQL){
            return new PostgreSQLDaoFactory();
        }
        //TODO throw exception
        return null;
    }
}
