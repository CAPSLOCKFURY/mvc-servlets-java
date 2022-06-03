package dao.factory.impl;

import dao.dao.*;
import dao.dao.impl.*;
import dao.factory.DaoFactory;
import db.ConnectionPool;

import java.sql.Connection;

public class PostgreSQLDaoFactory implements DaoFactory {

    @Override
    public UserDao getUserDao() {
        return new PostgreSQLUserDao(ConnectionPool.getConnection());
    }

    @Override
    public RoomsDao getRoomsDao() {
        return new PostgreSQLRoomsDao(ConnectionPool.getConnection());
    }

    @Override
    public RoomRequestDao getRoomRequestDao() {
        return new PostgreSQLRoomRequestDao(ConnectionPool.getConnection());
    }

    @Override
    public BillingDao getBillingDao(){
        return new PostgreSQLBillingDao(ConnectionPool.getConnection());
    }

    @Override
    public RoomRegistryDAO getRoomRegistryDao() {
        return new PostgreSQLRoomRegistryDAO(ConnectionPool.getConnection());
    }

    @Override
    public UserDao getUserDao(Connection connection) {
        return new PostgreSQLUserDao(connection);
    }

    @Override
    public RoomsDao getRoomsDao(Connection connection) {
        return new PostgreSQLRoomsDao(connection);
    }

    @Override
    public RoomRequestDao getRoomRequestDao(Connection connection) {
        return new PostgreSQLRoomRequestDao(connection);
    }

    @Override
    public BillingDao getBillingDao(Connection connection) {
        return new PostgreSQLBillingDao(connection);
    }

    @Override
    public RoomRegistryDAO getRoomRegistryDao(Connection connection) {
        return new PostgreSQLRoomRegistryDAO(connection);
    }
}
