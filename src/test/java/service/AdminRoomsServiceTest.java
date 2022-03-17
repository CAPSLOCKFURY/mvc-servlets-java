package service;

import db.ConnectionPool;
import models.Room;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.enums.RoomOrdering;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminRoomsServiceTest {

    private final static AdminRoomsService service = AdminRoomsService.getInstance();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    public void assignRoomToRequestTest(){
        boolean result = service.assignRoomToRequest(12L, 3L);
        assertTrue(result);
    }

    @Test
    public void findSuitableRoomsForRequestTest(){
        Pageable pageable = new Pageable(1, 15);
        LocalDateTime today = new java.sql.Date(System.currentTimeMillis()).toLocalDate().atStartOfDay();
        LocalDateTime todayPlus7 = today.plusDays(7);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Orderable orderable = new Orderable(RoomOrdering.ID.getColName(), OrderDirection.ASC);

        List<Room> rooms = service.findSuitableRoomsForRequest("en", Date.valueOf(today.format(dateFormat)), Date.valueOf(todayPlus7.format(dateFormat)), orderable, pageable);
        assertEquals(9, rooms.size());
        List<Room> rooms2 = service.findSuitableRoomsForRequest("en", Date.valueOf(today.plusDays(14).format(dateFormat)), Date.valueOf(todayPlus7.plusDays(36).format(dateFormat)), orderable, pageable);
        assertEquals(10, rooms2.size());
    }

}
