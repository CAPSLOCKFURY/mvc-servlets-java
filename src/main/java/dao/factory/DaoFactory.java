package dao.factory;

import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.dao.UserDao;

import java.sql.Connection;

public interface DaoFactory {
    UserDao getUserDao();

    RoomsDao getRoomsDao();

    RoomRequestDao getRoomRequestDao();

    BillingDao getBillingDao();

    UserDao getUserDao(Connection connection);

    RoomsDao getRoomsDao(Connection connection);

    RoomRequestDao getRoomRequestDao(Connection connection);

    BillingDao getBillingDao(Connection connection);
}
