package service;

import dao.dao.BillingDao;
import dao.dao.RoomRegistryDAO;
import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import exceptions.db.DaoException;
import forms.RoomRequestForm;
import models.Billing;
import models.Room;
import models.RoomRegistry;
import models.RoomRequest;
import models.base.pagination.Pageable;
import web.base.messages.MessageTransport;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class RoomRequestService {

    private RoomRequestService(){

    }

    private static final class SingletonHolder{
        static final RoomRequestService instance = new RoomRequestService();
    }

    public static RoomRequestService getInstance(){
        return RoomRequestService.SingletonHolder.instance;
    }

    public boolean createRoomRequest(RoomRequestForm form){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao()) {
            return roomRequestDao.createRoomRequest(new RoomRequest(form));
        }
    }

    public List<RoomRequest> getRoomRequestsByUserId(Long userId, String locale, Pageable pageable){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao()) {
            return roomRequestDao.getAllRoomRequestsByUserId(userId, locale, pageable);
        }
    }

    public boolean disableRoomRequest(Long requestId, Long userId, MessageTransport messageTransport){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao()) {
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            if (!roomRequest.getUserId().equals(userId)) {
                messageTransport.addLocalizedMessage("message.notYourRequest");
                return false;
            }
            if (!roomRequest.getStatus().equals("awaiting")) {
                messageTransport.addLocalizedMessage("message.disableRequestWrongStatus");
                return false;
            }
            roomRequest.setStatus("closed");
            return roomRequestDao.updateRoomRequest(roomRequest);
        }
    }

    public boolean confirmRoomRequest(Long requestId, Long userId, MessageTransport messageTransport){
        RoomRequestDao roomRequestDao = null;
        try {
            roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();
            RoomsDao roomDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao(roomRequestDao.getConnection());
            RoomRegistryDAO roomRegistryDAO = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRegistryDao(roomRequestDao.getConnection());
            BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao(roomRequestDao.getConnection());
            roomRequestDao.transaction.open();
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            if (!roomRequest.getUserId().equals(userId)) {
                messageTransport.addLocalizedMessage("message.notYourRequest");
                return false;
            }
            if (!roomRequest.getStatus().equals("awaiting confirmation")) {
                messageTransport.addLocalizedMessage("message.confirmRequestWrongStatus");
                return false;
            }
            Room room = roomDao.getRoomById(roomRequest.getRoomId(), "en");
            long differenceInDays = Duration.between(roomRequest.getCheckInDate().toLocalDate().atStartOfDay(), roomRequest.getCheckOutDate().toLocalDate().atStartOfDay()).toDays();
            BigDecimal decimalDifferenceInDays = new BigDecimal(differenceInDays);
            BigDecimal roomPrice = room.getPrice().multiply(decimalDifferenceInDays);

            roomRequest.setStatus("awaiting payment");
            roomRequestDao.updateRoomRequest(roomRequest);
            RoomRegistry roomRegistry = new RoomRegistry(userId, roomRequest.getRoomId(), roomRequest.getCheckInDate(), roomRequest.getCheckOutDate());
            long roomRegistryId = roomRegistryDAO.createRoomRegistry(roomRegistry);

            Billing billing = new Billing(roomRequest.getId(), roomPrice, roomRegistryId);
            billingDao.createBilling(billing);

            roomRequestDao.transaction.commit();
            return true;
        } catch (DaoException daoException){
            roomRequestDao.transaction.rollback();
            return false;
        } finally {
            roomRequestDao.close();
        }
    }

    public boolean declineAssignedRoom(String comment, Long userId, Long requestId, MessageTransport messageTransport){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao()) {
            RoomRequest roomRequest = roomRequestDao.getRoomRequestById(requestId);
            if (!roomRequest.getUserId().equals(userId)) {
                messageTransport.addLocalizedMessage("message.notYourRequest");
                return false;
            }
            if (!roomRequest.getStatus().equals("awaiting confirmation")) {
                messageTransport.addLocalizedMessage("message.wrongRequestStatus");
                return false;
            }

            roomRequest.setComment(comment);
            roomRequest.setRoomId(null);
            roomRequest.setStatus("awaiting");
            return roomRequestDao.updateRoomRequest(roomRequest);
        }
    }
}
