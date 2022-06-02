package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.CloseRoomForm;
import forms.ReportConfigurationForm;
import models.Room;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.dto.OverlapCountDTO;
import models.dto.RoomRegistryPdfReportDto;

import java.util.Collections;
import java.util.List;

public class AdminRoomsService {
    //TODO add logging to all services
    //private static final RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
    //private static final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    private AdminRoomsService(){

    }

    private static final class SingletonHolder{
        static final AdminRoomsService instance = new AdminRoomsService();
    }

    public static AdminRoomsService getInstance(){
        return AdminRoomsService.SingletonHolder.instance;
    }

    public List<Room> findSuitableRoomsForRequest(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate, Orderable orderable, Pageable pageable){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();) {
            return roomsDao.findSuitableRoomsForDates(locale, checkInDate, checkOutDate, orderable, pageable);
        }
    }

    public boolean assignRoomToRequest(Long roomId, Long requestId){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
            RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(roomsDao.getConnection());)
        {
            AdminRoomRequestDTO roomRequest = roomRequestDao.getRoomRequestForAdmin(requestId, "en");
            OverlapCountDTO overlapCount = roomsDao.getDatesOverlapCount(roomRequest.getCheckInDate(), roomRequest.getCheckOutDate(), roomId);
            if (overlapCount.getCount() != 0) {
                return false;
            }
            return roomsDao.assignRoomToRequest(roomId, requestId);
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
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();){
            return roomsDao.setRoomUnavailableAndRefundMoney(id, form.getEndDate());
        } catch (DaoException sqle){
            form.addError("Database error");
            return false;
        }
    }

    public boolean openRoom(Long id){
        try(RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();) {
            return roomsDao.openRoom(id);
        }
    }
}
