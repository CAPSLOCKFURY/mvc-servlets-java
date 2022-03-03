package dao.factory;

import dao.dao.UserDao;

public abstract class DaoFactory {
    public abstract UserDao getUserDao();
}
