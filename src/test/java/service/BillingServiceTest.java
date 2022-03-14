package service;

import commands.base.messages.CookieMessageTransport;
import commands.base.messages.MessageTransport;
import models.Billing;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillingServiceTest {

    private final static BillingService service = BillingService.getInstance();

    @Test
    @Order(1)
    public void findBillingsByUserIdTest(){
        Pageable pageable = new Pageable(1, 10);
        List<Billing> billings = service.findBillingsByUserId(3L, pageable);
        assertEquals(1, billings.size());
    }

    @Test
    @Order(2)
    public void payBillingTest(){
        MessageTransport messageTransport = new CookieMessageTransport();
        boolean result = service.payBilling(3L, 1L, messageTransport);
        assertTrue(result);
    }

}
