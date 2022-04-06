package dao.factory;

import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.dao.UserDao;

public interface DaoFactory {
    UserDao getUserDao();

    RoomsDao getRoomsDao();

    RoomRequestDao getRoomRequestDao();

    BillingDao getBillingDao();
}
