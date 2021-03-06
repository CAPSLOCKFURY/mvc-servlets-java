package service;

import context.ContextHolder;
import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import models.RoomRequest;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestStatus;

import java.util.List;

public class AdminRoomRequestService {

    private final SqlDB sqlDB;

    private AdminRoomRequestService(){
        sqlDB = ContextHolder.getInstance().getApplicationContext().sqlDb();
    }

    private static final class SingletonHolder{
        static final AdminRoomRequestService instance = new AdminRoomRequestService();
    }

    public static AdminRoomRequestService getInstance(){
        return AdminRoomRequestService.SingletonHolder.instance;
    }


    public AdminRoomRequestDTO getAdminRoomRequestById(Long requestId, String locale){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(sqlDB).getRoomRequestDao();) {
            return roomRequestDao.getRoomRequestForAdmin(requestId, locale);
        }
    }

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale, RoomRequestStatus requestStatus, Orderable orderable, Pageable pageable){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(sqlDB).getRoomRequestDao();) {
            return roomRequestDao.getRoomRequestsForAdmin(locale, requestStatus.getColName(), orderable, pageable);
        }
    }

    public boolean closeRoomRequest(Long requestId, String comment){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(sqlDB).getRoomRequestDao();) {
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            roomRequest.setStatus("closed");
            roomRequest.setManagerComment(comment);
            roomRequest.setRoomId(null);
            return roomRequestDao.updateRoomRequest(roomRequest);
        }
    }

}
