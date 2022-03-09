package service;

import dao.dao.RoomRequestDao;
import dao.dao.impl.PostgreSQLRoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.RoomRequest;
import models.dto.AdminRoomRequestDTO;

import java.sql.SQLException;
import java.util.List;

public class AdminRoomRequestService {
    private final static RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    public AdminRoomRequestDTO getAdminRoomRequestById(Long requestId, String locale){
        try{
            return roomRequestDao.getRoomRequestForAdmin(requestId, locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale){
        try{
            return roomRequestDao.getRoomRequestsForAdmin(locale);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DaoException();
        }
    }
}
