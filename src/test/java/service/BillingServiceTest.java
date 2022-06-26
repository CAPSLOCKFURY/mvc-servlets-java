package service;

import dao.dao.BillingDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import web.base.messages.CookieMessageTransport;
import web.base.messages.MessageTransport;
import db.ConnectionPool;
import models.Billing;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillingServiceTest {

    private final static BillingService service = BillingService.getInstance();

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
    @Order(1)
    void findBillingsByUserIdTest(){
        Pageable pageable = new Pageable(1, 10);
        List<Billing> billings = service.findBillingsByUserId(3L, pageable);
        assertEquals(1, billings.size());
    }

    @Test
    @Order(2)
    void payBillingTest(){
        BillingDao billingDao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getBillingDao(connection);
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.payBilling(3L, 1L, messageTransport);
        assertTrue(result);
        Billing billing = billingDao.getBillingById(1L);
        assertEquals(true, billing.getPaid());
    }

}
