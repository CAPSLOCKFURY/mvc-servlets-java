package dao.factory;

import dao.dao.RoomsDao;
import dao.dao.UserDao;

public abstract class DaoFactory {
    public abstract UserDao getUserDao();

    public abstract RoomsDao getRoomsDao();
}
