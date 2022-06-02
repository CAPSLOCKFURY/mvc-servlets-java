package dao.factory.impl;

import dao.dao.BillingDao;
import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.dao.UserDao;
import dao.dao.impl.PostgreSQLBillingDao;
import dao.dao.impl.PostgreSQLRoomRequestDao;
import dao.dao.impl.PostgreSQLRoomsDao;
import dao.dao.impl.PostgreSQLUserDao;
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
}
