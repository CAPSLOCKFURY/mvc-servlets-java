package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.ReportConfigurationForm;
import models.Room;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.dto.OverlapCountDTO;
import models.dto.RoomRegistryPdfReportDto;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AdminRoomsService {
    //TODO add logging to all services
    private final static RoomsDao roomsDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();
    private final static RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    private AdminRoomsService(){

    }

    private static final class SingletonHolder{
        static final AdminRoomsService instance = new AdminRoomsService();
    }

    public static AdminRoomsService getInstance(){
        return AdminRoomsService.SingletonHolder.instance;
    }

    public List<Room> findSuitableRoomsForRequest(String locale, java.sql.Date checkInDate, java.sql.Date checkOutDate, Orderable orderable, Pageable pageable){
        try{
            return roomsDao.findSuitableRoomsForDates(locale, checkInDate, checkOutDate, orderable, pageable);
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

    public List<RoomRegistryPdfReportDto> findDataForRoomRegistryReport(ReportConfigurationForm form, Pageable pageable){
        try{
            return roomsDao.findDataForRoomRegistryReport(form.getCheckInDate(), form.getCheckOutDate(), pageable);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return Collections.emptyList();
        }
    }
}
