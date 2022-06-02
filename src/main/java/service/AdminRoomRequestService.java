package service;

import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestStatus;

import java.util.List;

public class AdminRoomRequestService {

    //private static final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    private AdminRoomRequestService(){

    }

    private static final class SingletonHolder{
        static final AdminRoomRequestService instance = new AdminRoomRequestService();
    }

    public static AdminRoomRequestService getInstance(){
        return AdminRoomRequestService.SingletonHolder.instance;
    }


    public AdminRoomRequestDTO getAdminRoomRequestById(Long requestId, String locale){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();) {
            return roomRequestDao.getRoomRequestForAdmin(requestId, locale);
        }
    }

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale, RoomRequestStatus requestStatus, Orderable orderable, Pageable pageable){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();) {
            return roomRequestDao.getRoomRequestsForAdmin(locale, requestStatus.getColName(), orderable, pageable);
        }
    }

    public boolean closeRoomRequest(Long requestId, String comment){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();) {
            return roomRequestDao.adminCloseRequest(requestId, comment);
        }
    }

}
