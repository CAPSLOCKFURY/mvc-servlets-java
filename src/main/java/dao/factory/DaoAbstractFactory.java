package dao.factory;

import dao.factory.impl.PostgreSQLDaoFactory;
import exceptions.db.DaoFactoryNotFound;

public class DaoAbstractFactory {

    public static DaoFactory getFactory(SqlDB type){
        if(type == SqlDB.POSTGRESQL){
            return new PostgreSQLDaoFactory();
        }
        throw new DaoFactoryNotFound();
    }

    private DaoAbstractFactory(){

    }
}
