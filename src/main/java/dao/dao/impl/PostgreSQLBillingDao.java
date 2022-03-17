package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.BillingDao;
import db.ConnectionPool;
import models.Billing;
import models.base.SqlColumn;
import models.base.SqlType;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLBillingDao extends BillingDao {

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
        return createEntity(connection, SqlQueries.Billing.INSERT_BILLING, new BillingForm());
    }

    @Override
    public ExtendedBillingDTO getBillingById(Long billingId) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneById(connection, SqlQueries.Billing.GET_BILLING_BY_ID, billingId, ExtendedBillingDTO.class);
        }
    }

    @Override
    public List<Billing> getAllBillingsByUserId(Long userId, Pageable pageable) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = userId;
                public Long getId() {return id;}
            }
            return getAllByParams(connection, SqlQueries.Billing.FIND_ALL_BILLING_BY_USER_ID, new Param(), Billing.class, pageable);
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
            boolean billingUpdated = updateEntity(connection, SqlQueries.Billing.PAY_BILLING, new Param());
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

    @Override
    public int deleteOldBillings() throws SQLException {
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            updatePlain(connection, SqlQueries.Billing.DELETE_ROOM_REQUESTS_CONNECTED_TO_OLD_BILLING);
            updatePlain(connection, SqlQueries.Billing.DELETE_ROOM_REGISTRIES_CONNECTED_TO_OLD_BILLING);
            int affectedRows = updatePlain(connection, SqlQueries.Billing.DELETE_OLD_BILLINGS);
            connection.commit();
            return affectedRows;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            connection.rollback();
            return -1;
        } finally {
            if(connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}
