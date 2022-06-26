package service;

import dao.dao.RoomRequestDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import web.base.messages.CookieMessageTransport;
import web.base.messages.MessageTransport;
import db.ConnectionPool;
import forms.RoomRequestForm;
import models.RoomRequest;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomRequestServiceTest {

    private final static RoomRequestService service = RoomRequestService.getInstance();

    public static Connection connection;

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
        connection = ConnectionPool.getConnection();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void getRoomRequestByUserId(){
        Pageable pageable = new Pageable(1, 10);
        List<RoomRequest> roomRequests = service.getRoomRequestsByUserId(2L, "en", pageable);
        assertEquals(1, roomRequests.size());
        assertEquals(2, roomRequests.get(0).getUserId());
    }

    @Test
    void createRoomRequestTest(){
        RoomRequestForm form = new RoomRequestForm();

        LocalDateTime today = new java.sql.Date(System.currentTimeMillis()).toLocalDate().atStartOfDay();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringToday = today.format(dateFormat);
        String stringTodayPlus7 = today.plusDays(7).format(dateFormat);

        form.setUserId(1L);
        form.setCapacity("2");
        form.setCheckInDate(stringToday);
        form.setCheckOutDate(stringTodayPlus7);
        form.setRoomClass("1");
        form.setComment("comment");
        boolean result = service.createRoomRequest(form);
        assertTrue(result);
    }

    @Test
    void confirmRoomRequestTest(){
        RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(connection);
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.confirmRoomRequest(2L, 3L, messageTransport);
        assertTrue(result);
        RoomRequest roomRequest = roomRequestDao.getRoomRequestById(2L);
        assertEquals("awaiting payment", roomRequest.getStatus());
    }

    @Test
    void declineAssignedRoomTest(){
        RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(connection);
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.declineAssignedRoom("123", 3L, 3L, messageTransport);
        assertTrue(result);
        RoomRequest roomRequest = roomRequestDao.getRoomRequestById(3L);
        assertEquals("awaiting", roomRequest.getStatus());
    }

    @Test
    void disableRoomRequestTest(){
        RoomRequestDao roomRequestDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getRoomRequestDao(connection);
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.disableRoomRequest(4L, 3L, messageTransport);
        assertTrue(result);
        RoomRequest roomRequest = roomRequestDao.getRoomRequestById(4L);
        assertEquals("closed", roomRequest.getStatus());
    }
}
