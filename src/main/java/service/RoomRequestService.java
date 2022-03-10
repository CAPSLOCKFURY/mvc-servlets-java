package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.RoomRequestForm;
import models.Room;
import models.RoomRequest;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class RoomRequestService {
    private final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();
    private final static RoomsDao roomDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();

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

    public boolean disableRoomRequest(Long requestId, Long userId){
        try{
            return roomRequestDao.disableRoomRequest(requestId, userId);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean confirmRoomRequest(Long requestId){
        try{
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            Room room = roomDao.getRoomById(roomRequest.getRoomId(), "en");
            long differenceInDays = Duration.between(roomRequest.getCheckInDate().toLocalDate().atStartOfDay(), roomRequest.getCheckOutDate().toLocalDate().atStartOfDay()).toDays();
            BigDecimal decimalDifferenceInDays = new BigDecimal(differenceInDays);
            BigDecimal roomPrice = room.getPrice().multiply(decimalDifferenceInDays);
            return roomRequestDao.confirmRoomRequest(roomRequest, roomPrice);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }
}
