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
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.OverlapCountDTO;
import models.dto.RoomExtendedInfo;
import models.dto.RoomHistoryDTO;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class RoomsService {

    //private static final RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
    //private static final UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();

    private RoomsService(){

    }

    private static final class SingletonHolder {
        static final RoomsService instance = new RoomsService();
    }

    public static RoomsService getInstance(){
        return SingletonHolder.instance;
    }

    public List<Room> getAllRooms(String locale, Orderable orderable, Pageable pageable){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.getAllRooms(locale, orderable, pageable);
        }
    }

    public List<RoomClass> getRoomClasses(String locale){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.getAllRoomClasses(locale);
        }
    }

    public Room getRoomById(Long id, String locale){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.getRoomById(id, locale);
        }
    }

    public RoomExtendedInfo getExtendedRoomInfo(Long id, String locale){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
        return roomsDao.getExtendedRoomInfoById(id , locale);
        }
    }

    public boolean bookRoom(BookRoomForm form, Long roomId, Long userId){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
            UserDao userDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao(roomsDao.getConnection());) {
            OverlapCountDTO overlapCountDTO = roomsDao.getDatesOverlapCount(form.getCheckInDate(), form.getCheckOutDate(), roomId);
            if(overlapCountDTO.getCount() != 0){
                form.addLocalizedError("errors.RoomDatesOverlap");
                return false;
            }
            User user = userDao.getUserById(userId);
            Room room = roomsDao.getRoomById(roomId, "en");
            if(room.getStatus().equals("unavailable")){
                form.addLocalizedError("errors.roomIsUnavailable");
                return false;
            }
            long differenceInDays = Duration.between(form.getCheckInDate().toLocalDate().atStartOfDay(), form.getCheckOutDate().toLocalDate().atStartOfDay()).toDays();
            BigDecimal decimalDifferenceInDays = new BigDecimal(differenceInDays);
            if(user.getBalance().compareTo(room.getPrice().multiply(decimalDifferenceInDays)) < 0){
                form.addLocalizedError("errors.NotEnoughMoney");
                return false;
            }
            BigDecimal roomPrice = room.getPrice().multiply(decimalDifferenceInDays);
            return roomsDao.bookRoom(form.getCheckInDate(), form.getCheckOutDate(), roomPrice, roomId, userId);
        } catch (DaoException daoException){
            form.addError("Database Error");
            return false;
        }
    }

    public List<RoomHistoryDTO> getUserRoomHistory(Long userId, String locale, Pageable pageable){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.getRoomHistory(userId, locale, pageable);
        }
    }

    public int archiveOldRoomRegistries(){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.archiveOldRoomRegistries();
        } catch (DaoException daoException){
            return -1;
        }
    }

    public int updateRoomsStatus(){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao()) {
            return roomsDao.updateRoomStatus();
        } catch (DaoException daoException){
            return -1;
        }
    }

}
