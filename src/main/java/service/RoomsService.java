package service;

import dao.dao.RoomsDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Room;
import models.RoomClass;
import models.dto.RoomExtendedInfo;

import java.sql.SQLException;
import java.util.List;

public class RoomsService {

    private final RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();

    public List<Room> getAllRooms(String locale){
        try{
            return roomsDao.getAllRooms(locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public List<RoomClass> getRoomClasses(String locale){
        try{
            return roomsDao.getAllRoomClasses(locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public Room getRoomById(Long id, String locale){
        try{
            return roomsDao.getRoomById(id, locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

}
