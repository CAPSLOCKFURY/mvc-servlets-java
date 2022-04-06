package service;

import db.ConnectionPool;
import forms.BookRoomForm;
import models.Room;
import models.RoomClass;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.RoomExtendedInfo;
import models.dto.RoomHistoryDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class RoomsServiceTest {

    private final static RoomsService service = RoomsService.getInstance();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void getAllRoomsTest(){
        Orderable orderable = new Orderable("id", OrderDirection.ASC);
        Pageable pageable = new Pageable(1, 10);
        List<Room> rooms = service.getAllRooms("en", orderable, pageable);
        assertEquals(10, rooms.size());
        assertEquals("Cheap", rooms.get(0).getClassName());
    }

    @Test
    void getAllRoomClassesTest(){
        List<RoomClass> roomClasses = service.getRoomClasses("en");
        assertEquals(4, roomClasses.size());
    }

    @Test
    void getRoomById(){
        Room room = service.getRoomById(1L, "en");
        assertEquals(1, room.getId());
        assertEquals("Cheap", room.getClassName());
    }

    @Test
    void getExtendedRoomInfoTest(){
        RoomExtendedInfo roomExtendedInfo = service.getExtendedRoomInfo(1L, "en");
        assertEquals(1, roomExtendedInfo.getId());
        assertEquals(1, roomExtendedInfo.getDates().size());
        LocalDateTime today = new java.sql.Date(System.currentTimeMillis()).toLocalDate().atStartOfDay();
        assertEquals(today, roomExtendedInfo.getDates().get(0).getCheckInDate().toLocalDate().atStartOfDay());
        LocalDateTime todayPlus7 = today.plusDays(7);
        assertEquals(todayPlus7, roomExtendedInfo.getDates().get(0).getCheckOutDate().toLocalDate().atStartOfDay());
    }

    @Test
    void getUserRoomHistoryTest(){
        Pageable pageable = new Pageable(1, 10);
        List<RoomHistoryDTO> roomHistory = service.getUserRoomHistory(1L, "en", pageable);
        assertEquals(1, roomHistory.size());
        assertEquals(1, roomHistory.get(0).getId());
    }

    @Test
    void bookRoomTest(){
        BookRoomForm form = new BookRoomForm();

        LocalDateTime today = new java.sql.Date(System.currentTimeMillis()).toLocalDate().atStartOfDay();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String stringToday = today.format(dateFormat);

        form.setCheckInDate(stringToday);

        LocalDateTime todayPlus7 = today.plusDays(7);
        form.setCheckOutDate(todayPlus7.format(dateFormat));
        form.setLocale(new Locale("en"));

        boolean result = service.bookRoom(form, 2L, 2L);
        assertTrue(result);

        boolean failedResult = service.bookRoom(form, 2L, 2L);
        assertFalse(failedResult);
        assertEquals(1, form.getErrors().size());
        assertTrue(form.getErrors().contains(ResourceBundle.getBundle("forms", new Locale("en")).getString("errors.RoomDatesOverlap")));
    }
}
