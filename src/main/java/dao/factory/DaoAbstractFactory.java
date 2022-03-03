package dao.factory;

import dao.factory.impl.MySqlDaoFactory;

public class DaoAbstractFactory {

    public static DaoFactory getFactory(SqlDB type){
        if(type == SqlDB.MYSQL){
            return new MySqlDaoFactory();
        }
        //TODO throw exception
        return null;
    }
}
