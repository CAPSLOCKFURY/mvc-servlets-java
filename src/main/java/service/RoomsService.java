package service;

import dao.dao.RoomsDao;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.User;
import models.base.pagination.Pageable;
import models.dto.OverlapCountDTO;
import models.dto.RoomExtendedInfo;
import models.dto.RoomHistoryDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

public class RoomsService {

    //TODO make this all static and class should be singleton
    private final static RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
    private final static UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    public List<Room> getAllRooms(String locale, Pageable pageable){
        try{
            return roomsDao.getAllRooms(locale, pageable);
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

    public RoomExtendedInfo getExtendedRoomInfo(Long id, String locale){
        try{
            return roomsDao.getExtendedRoomInfoById(id , locale);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean bookRoom(BookRoomForm form, Long roomId, Long userId){
        try{
            OverlapCountDTO overlapCountDTO = roomsDao.getDatesOverlapCount(form.getCheckInDate(), form.getCheckOutDate(), roomId);
            if(overlapCountDTO.getCount() != 0){
                form.addLocalizedError("errors.RoomDatesOverlap");
                return false;
            }
            User user = userDao.getUserById(userId);
            Room room = roomsDao.getRoomById(roomId, "en");
            //TODO make function for this
            long differenceInDays = Duration.between(form.getCheckInDate().toLocalDate().atStartOfDay(), form.getCheckOutDate().toLocalDate().atStartOfDay()).toDays();
            BigDecimal decimalDifferenceInDays = new BigDecimal(differenceInDays);
            if(user.getBalance().compareTo(room.getPrice().multiply(decimalDifferenceInDays)) < 0){
                form.addLocalizedError("errors.NotEnoughMoney");
                return false;
            }
            BigDecimal roomPrice = room.getPrice().multiply(decimalDifferenceInDays);
            return roomsDao.bookRoom(form, roomPrice, roomId, userId);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            form.addError("Database Error");
            return false;
        }
    }

    public List<RoomHistoryDTO> getUserRoomHistory(Long userId, String locale){
        try{
            return roomsDao.getRoomHistory(userId, locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

}
