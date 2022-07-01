package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.CloseRoomForm;
import forms.ReportConfigurationForm;
import models.Room;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.IsRoomAssigned;
import models.dto.OverlapCountDTO;
import models.dto.RoomRegistryPdfReportDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class AdminRoomsService {

    private AdminRoomsService(){

    }

    private static final class SingletonHolder{
        static final AdminRoomsService instance = new AdminRoomsService();
    }

    public static AdminRoomsService getInstance(){
        return AdminRoomsService.SingletonHolder.instance;
    }

    public List<Room> findSuitableRoomsForRequest(String locale, LocalDate checkInDate, LocalDate checkOutDate, Orderable orderable, Pageable pageable){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();) {
            return roomsDao.findSuitableRoomsForDates(locale, checkInDate, checkOutDate, orderable, pageable);
        }
    }

    public boolean assignRoomToRequest(Long roomId, Long requestId){
        RoomsDao roomsDao = null;
        try {
            roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
            RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(roomsDao.getConnection());
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            IsRoomAssigned isRoomAssigned = roomRequestDao.isRoomAssigned(roomId, roomRequest.getCheckInDate(), roomRequest.getCheckOutDate());
            if(isRoomAssigned.getAssigned()){
                return false;
            }
            Room room = roomsDao.getRoomById(roomId, "en");
            if(room.getStatus().equals("unavailable")){
                return false;
            }
            OverlapCountDTO overlapCount = roomsDao.getDatesOverlapCount(roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), roomId);
            if (overlapCount.getCount() != 0) {
                return false;
            }
            if(!roomRequest.getStatus().equals("awaiting")){
                return false;
            }
            roomRequest.setStatus("awaiting confirmation");
            roomRequest.setRoomId(roomId);
            return roomRequestDao.updateRoomRequest(roomRequest);
        } finally {
            roomsDao.close();
        }
    }

    public List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(ReportConfigurationForm form, Pageable pageable){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();){
            return roomsDao.findDataForRoomRegistryReport(form.getCheckInDate(), form.getCheckOutDate(), pageable);
        } catch (DaoException daoException){
            return Collections.emptyList();
        }
    }

    public boolean closeRoom(Long id, CloseRoomForm form){
        RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
        try{
            roomsDao.transaction.open();
            boolean result = roomsDao.setRoomUnavailableAndRefundMoney(id, form.getEndDate());
            roomsDao.transaction.commit();
            return result;
        } catch (DaoException sqle){
            form.addError("Database error");
            roomsDao.transaction.rollback();
            return false;
        } finally {
            roomsDao.transaction.close();
        }
    }

    public boolean openRoom(Long id){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();) {
            Room room = roomsDao.getRoomById(id, "en");
            room.setStatus("free");
            return roomsDao.updateRoom(room);
        }
    }
}
