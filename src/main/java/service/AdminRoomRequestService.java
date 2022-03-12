package service;

import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.base.pagination.Pageable;
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

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale, Pageable pageable){
        try{
            return roomRequestDao.getRoomRequestsForAdmin(locale, pageable);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean closeRoomRequest(Long requestId, String comment){
        try{
            return roomRequestDao.adminCloseRequest(requestId, comment);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }
}
