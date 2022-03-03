package dao.factory.impl;

import dao.dao.UserDao;
import dao.dao.impl.MySqlUserDao;
import dao.factory.DaoFactory;

public class MySqlDaoFactory extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }
}
