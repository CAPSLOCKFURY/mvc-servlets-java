package service;

import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import db.ConnectionPool;
import forms.RoomRequestForm;
import models.RoomRequest;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomRequestServiceTest {

    private final static RoomRequestService service = RoomRequestService.getInstance();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
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
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.confirmRoomRequest(2L, 3L, messageTransport);
        assertTrue(result);
    }

    @Test
    void declineAssignedRoomTest(){
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.declineAssignedRoom("123", 3L, 3L, messageTransport);
        assertTrue(result);
    }

    @Test
    void disableRoomRequestTest(){
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.disableRoomRequest(4L, 3L, messageTransport);
        assertTrue(result);
    }
}
