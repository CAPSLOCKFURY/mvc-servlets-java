package dao.factory.impl;

import dao.dao.RoomsDao;
import dao.dao.UserDao;
import dao.dao.impl.PostgreSQLRoomsDao;
import dao.dao.impl.PostgreSQLUserDao;
import dao.factory.DaoFactory;

public class PostgreSQLDaoFactory extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        return new PostgreSQLUserDao();
    }

    @Override
    public RoomsDao getRoomsDao() {
        return new PostgreSQLRoomsDao();
    }


}
