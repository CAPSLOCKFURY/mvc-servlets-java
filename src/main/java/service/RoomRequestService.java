package service;

import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.RoomRequestForm;
import models.RoomRequest;

import java.sql.SQLException;
import java.util.List;

public class RoomRequestService {
    private final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    public boolean createRoomRequest(RoomRequestForm form){
        try{
            return roomRequestDao.createRoomRequest(form);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public List<RoomRequest> getRoomRequestsByUserId(Long userId, String locale){
        try{
            return roomRequestDao.getAllRoomRequestsByUserId(userId, locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }
}
