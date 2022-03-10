package dao.dao.impl;

import dao.dao.BillingDao;
import db.ConnectionPool;
import models.Billing;
import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLBillingDao extends BillingDao {

    private final static String INSERT_BILLING = "insert into billing(request_id, price) values (?, ?)";

    private final static String FIND_ALL_BILLING_BY_USER_ID = "select * from billing \n" +
            "    left outer join room_requests rr on billing.request_id = rr.id\n" +
            "where rr.user_id = ?";

    @Override
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

    @Override
    public List<Billing> getAllBillingsByUserId(Long userId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = userId;
                public Long getId() {return id;}
            }
            return getAllByParams(connection, FIND_ALL_BILLING_BY_USER_ID, new Param(), Billing.class);
        }
    }
}
