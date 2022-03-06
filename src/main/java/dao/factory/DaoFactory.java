package dao.factory;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.dao.UserDao;

public abstract class DaoFactory {
    public abstract UserDao getUserDao();

    public abstract RoomsDao getRoomsDao();

    public abstract RoomRequestDao getRoomRequestDao();
}
