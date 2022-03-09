package service;

import dao.dao.impl.PostgreSQLRoomRequestDao;
import exceptions.db.DaoException;
import models.dto.AdminRoomRequestDTO;

import java.sql.SQLException;
import java.util.List;

public class AdminRoomRequestService {
    private final static PostgreSQLRoomRequestDao roomRequestDao = new PostgreSQLRoomRequestDao();

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale){
        try{
            return roomRequestDao.getRoomRequestsForAdmin(locale);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DaoException();
        }
    }
}
