package dao.dao.impl;

import dao.dao.BillingDao;
import db.ConnectionPool;
import models.Billing;
import models.base.SqlColumn;
import models.base.SqlType;
import models.dto.ExtendedBillingDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLBillingDao extends BillingDao {

    private final static String INSERT_BILLING = "insert into billing(request_id, price, room_registry_id) values (?, ?, ?)";

    private final static String FIND_ALL_BILLING_BY_USER_ID = "select * from billing \n" +
            "    left outer join room_requests rr on billing.request_id = rr.id\n" +
            "where rr.user_id = ?";

    private final static String PAY_BILLING = "update billing set paid = true where id = ?";

    private final static String GET_BILLING_BY_ID = "select *, rr.id as request_id, rr.user_id as user_id from billing \n" +
            "    left outer join room_requests rr on billing.request_id = rr.id\n" +
            "where billing.id = ?";

    @Override
    public boolean insertBilling(Connection connection, Long requestId, BigDecimal price, Long roomRegistryId) throws SQLException {
        class BillingForm{
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long reqId = requestId;
            @SqlColumn(columnName = "", type = SqlType.DECIMAL)
            private final BigDecimal billingPrice = price;
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long roomRegistryInsertId = roomRegistryId;
            public Long getReqId() {return reqId;}
            public BigDecimal getBillingPrice() {return billingPrice;}
            public Long getRoomRegistryInsertId() {return roomRegistryInsertId;}
        }
        return createEntity(connection, INSERT_BILLING, new BillingForm());
    }

    @Override
    public ExtendedBillingDTO getBillingById(Long billingId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneById(connection, GET_BILLING_BY_ID, billingId, ExtendedBillingDTO.class);
        }
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

    @Override
    public boolean payBilling(Long userId, ExtendedBillingDTO billing) throws SQLException{
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            class MoneyParam{
                @SqlColumn(columnName = "", type = SqlType.DECIMAL)
                private final BigDecimal amount = billing.getPrice();
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = userId;
                public BigDecimal getAmount() {return amount;}
                public Long getId() {return id;}
            }
            boolean balanceUpdated = updateEntity(connection, "update users set balance = balance - ? where id =?", new MoneyParam());
            if(!balanceUpdated){
                connection.rollback();
                return false;
            }
            class Param{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = billing.getId();
                public Long getId() {return id;}
            }
            boolean billingUpdated = updateEntity(connection, PAY_BILLING, new Param());
            if(!billingUpdated){
                connection.rollback();
                return false;
            }
            class UpdateParam{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = billing.getRequestId();
                public Long getId() {return id;}
            }
            boolean requestStatusUpdated = updateEntity(connection,
                    "update room_requests set status = 'paid' where id = ?", new UpdateParam());
            if(!requestStatusUpdated){
                connection.rollback();
                return false;
            }
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}
