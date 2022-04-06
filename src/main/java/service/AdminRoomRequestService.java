package service;

import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestStatus;

import java.sql.SQLException;
import java.util.List;

public class AdminRoomRequestService {

    private static final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();

    private AdminRoomRequestService(){

    }

    private static final class SingletonHolder{
        static final AdminRoomRequestService instance = new AdminRoomRequestService();
    }

    public static AdminRoomRequestService getInstance(){
        return AdminRoomRequestService.SingletonHolder.instance;
    }


    public AdminRoomRequestDTO getAdminRoomRequestById(Long requestId, String locale){
        try{
            return roomRequestDao.getRoomRequestForAdmin(requestId, locale);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public List<AdminRoomRequestDTO> getAdminRoomRequests(String locale, RoomRequestStatus requestStatus, Orderable orderable, Pageable pageable){
        try{
            return roomRequestDao.getRoomRequestsForAdmin(locale, requestStatus.getColName(), orderable, pageable);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean closeRoomRequest(Long requestId, String comment){
        try {
            return roomRequestDao.adminCloseRequest(requestId, comment);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

}
