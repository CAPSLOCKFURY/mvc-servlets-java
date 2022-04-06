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

public class PostgreSQLDaoFactory implements DaoFactory {

    @Override
    public UserDao getUserDao() {
        return new PostgreSQLUserDao();
    }

    @Override
    public RoomsDao getRoomsDao() {
        return new PostgreSQLRoomsDao();
    }

    @Override
    public RoomRequestDao getRoomRequestDao() {
        return new PostgreSQLRoomRequestDao();
    }

    @Override
    public BillingDao getBillingDao(){
        return new PostgreSQLBillingDao();
    }
}
