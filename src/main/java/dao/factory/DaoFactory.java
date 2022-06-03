package dao.factory;

import dao.dao.*;

import java.sql.Connection;

public interface DaoFactory {
    UserDao getUserDao();

    RoomsDao getRoomsDao();

    RoomRequestDao getRoomRequestDao();

    BillingDao getBillingDao();

    RoomRegistryDAO getRoomRegistryDao();

    UserDao getUserDao(Connection connection);

    RoomsDao getRoomsDao(Connection connection);

    RoomRequestDao getRoomRequestDao(Connection connection);

    BillingDao getBillingDao(Connection connection);

    RoomRegistryDAO getRoomRegistryDao(Connection connection);
}
