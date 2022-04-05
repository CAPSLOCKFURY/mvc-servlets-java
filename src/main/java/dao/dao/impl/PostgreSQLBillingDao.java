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
        return createEntity(connection, SqlQueries.Billing.INSERT_BILLING, new Object[]{requestId, price, roomRegistryId});
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
            return getAllByParams(connection, SqlQueries.Billing.FIND_ALL_BILLING_BY_USER_ID, new Object[]{userId}, Billing.class, pageable);
        }
    }

    @Override
    public boolean payBilling(Long userId, ExtendedBillingDTO billing) throws SQLException{
        Connection connection = null;
        try{
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            boolean balanceUpdated = updateEntity(connection, "update users set balance = balance - ? where id =?", new Object[]{billing.getPrice(), userId});
            if(!balanceUpdated){
                connection.rollback();
                return false;
            }
            boolean billingUpdated = updateEntity(connection, SqlQueries.Billing.PAY_BILLING, new Object[]{billing.getId()});
            if(!billingUpdated){
                connection.rollback();
                return false;
            }
            boolean requestStatusUpdated = updateEntity(connection,
                    "update room_requests set status = 'paid' where id = ?", new Object[]{billing.getRequestId()});
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
            if(connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
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
