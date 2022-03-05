package service;

import dao.dao.RoomsDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Room;

import java.sql.SQLException;
import java.util.List;

public class RoomsService {

    private final RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();

    public List<Room> getAllRooms(){
        try{
            return roomsDao.getAllRooms();
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

}
