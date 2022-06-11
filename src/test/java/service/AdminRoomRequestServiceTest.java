package service;

import db.ConnectionPool;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import models.dto.AdminRoomRequestDTO;
import models.enums.RoomRequestStatus;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminRoomRequestServiceTest {

    private final static AdminRoomRequestService service = AdminRoomRequestService.getInstance();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    @Order(1)
    void getAdminRoomRequestByIdTest(){
        AdminRoomRequestDTO roomRequest = service.getAdminRoomRequestById(2L, "en");
        assertEquals(2, roomRequest.getId());
        assertEquals("awaiting confirmation", roomRequest.getStatus());
    }

    @Test
    @Order(2)
    void getAdminRoomRequestsTest(){
        Orderable orderable = new Orderable("id", OrderDirection.ASC);
        Pageable pageable = new Pageable(1, 15);
        List<AdminRoomRequestDTO> roomRequests = service.getAdminRoomRequests("en", RoomRequestStatus.AWAITING, orderable, pageable);
        assertEquals(1, roomRequests.size());
        List<AdminRoomRequestDTO> roomRequests2 = service.getAdminRoomRequests("en", RoomRequestStatus.PAID, orderable, pageable);
        assertEquals(1, roomRequests2.size());
    }

    @Test
    @Order(3)
    void closeRoomRequest(){
        boolean result = service.closeRoomRequest(5L, "closed");
        assertTrue(result);
    }
}
