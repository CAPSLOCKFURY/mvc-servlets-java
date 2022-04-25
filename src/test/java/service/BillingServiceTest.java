package service;

import web.base.messages.CookieMessageTransport;
import web.base.messages.MessageTransport;
import db.ConnectionPool;
import models.Billing;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillingServiceTest {

    private final static BillingService service = BillingService.getInstance();

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
    void findBillingsByUserIdTest(){
        Pageable pageable = new Pageable(1, 10);
        List<Billing> billings = service.findBillingsByUserId(3L, pageable);
        assertEquals(1, billings.size());
    }

    @Test
    @Order(2)
    void payBillingTest(){
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.payBilling(3L, 1L, messageTransport);
        assertTrue(result);
    }

}
