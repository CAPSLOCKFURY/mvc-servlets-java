package dao.dao.impl;

import dao.dao.BillingDao;
import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSQLBillingDao extends BillingDao {

    private final static String INSERT_BILLING = "insert into billing(request_id, price) values (?, ?)";

    public boolean insertBilling(Connection connection, Long requestId, BigDecimal price) throws SQLException {
        class BillingForm{
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long reqId = requestId;
            @SqlColumn(columnName = "", type = SqlType.DECIMAL)
            private final BigDecimal billingPrice = price;
            public Long getReqId() {return reqId;}
            public BigDecimal getBillingPrice() {return billingPrice;}
        }
        return createEntity(connection, INSERT_BILLING, new BillingForm());
    }
}
