package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.dao.impl.PostgreSQLRoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.Room;
import models.RoomRequest;
import models.dto.AdminRoomRequestDTO;
import models.dto.OverlapCountDTO;

import java.sql.SQLException;
import java.util.List;

public class AdminRoomsService {
    //TODO add logging to all services
    private final static RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
    private final static RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    public List<Room> findSuitableRoomsForRequest(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate){
        try{
            return roomsDao.findSuitableRoomsForDates(locale, checkInDate, checkOutDate);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean assignRoomToRequest(Long roomId, Long requestId){
        try{
            AdminRoomRequestDTO roomRequest = roomRequestDao.getRoomRequestForAdmin(requestId, "en");
            OverlapCountDTO overlapCount = roomsDao.getDatesOverlapCount(roomRequest.getCheckInDate(),roomRequest.getCheckOutDate(), roomId);
            if(overlapCount.getCount() != 0) {
                return false;
            }
            return roomsDao.assignRoomToRequest(roomId, requestId);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }
}
