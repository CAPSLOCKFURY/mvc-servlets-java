package service;

import dao.dao.RoomRequestDao;
import dao.dao.RoomsDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import forms.RoomRequestForm;
import models.Room;
import models.RoomRequest;
import models.base.pagination.Pageable;
import web.base.messages.MessageTransport;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class RoomRequestService {

    //private final RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();
    //private static final RoomsDao roomDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao();

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
            return roomRequestDao.disableRoomRequest(requestId, userId);
        }
    }

    public boolean confirmRoomRequest(Long requestId, Long userId, MessageTransport messageTransport){
        try(RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao();
            RoomsDao roomDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomsDao(roomRequestDao.getConnection());)
        {
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
            return roomRequestDao.confirmRoomRequest(roomRequest, roomPrice);
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
            return roomRequestDao.declineAssignedRoom(comment, requestId);
        }
    }
}
