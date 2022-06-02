package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.BillingDao;
import db.ConnectionPool;
import models.Billing;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLBillingDao extends BillingDao {

    @Override
    public boolean insertBilling(Long requestId, BigDecimal price, Long roomRegistryId) {
        return createEntity(SqlQueries.Billing.INSERT_BILLING, new Object[]{requestId, price, roomRegistryId});
    }

    @Override
    public ExtendedBillingDTO getBillingById(Long billingId) {
        return getOneById(SqlQueries.Billing.GET_BILLING_BY_ID, billingId, ExtendedBillingDTO.class);
    }

    @Override
    public List<Billing> getAllBillingsByUserId(Long userId, Pageable pageable) {
        return getAllByParams(SqlQueries.Billing.FIND_ALL_BILLING_BY_USER_ID, new Object[]{userId}, Billing.class, pageable);
    }

    @Override
    public boolean payBilling(Long userId, ExtendedBillingDTO billing) throws SQLException{
        try{
            connection.setAutoCommit(false);
            boolean balanceUpdated = updateEntity("update users set balance = balance - ? where id =?", new Object[]{billing.getPrice(), userId});
            if(!balanceUpdated){
                connection.rollback();
                return false;
            }
            boolean billingUpdated = updateEntity(SqlQueries.Billing.PAY_BILLING, new Object[]{billing.getId()});
            if(!billingUpdated){
                connection.rollback();
                return false;
            }
            boolean requestStatusUpdated = updateEntity("update room_requests set status = 'paid' where id = ?", new Object[]{billing.getRequestId()});
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
                //connection.close();
            }
        }
    }

    @Override
    public int deleteOldBillings() throws SQLException {
        try{
            connection.setAutoCommit(false);
            updatePlain(SqlQueries.Billing.DELETE_ROOM_REQUESTS_CONNECTED_TO_OLD_BILLING);
            updatePlain(SqlQueries.Billing.DELETE_ROOM_REGISTRIES_CONNECTED_TO_OLD_BILLING);
            int affectedRows = updatePlain(SqlQueries.Billing.DELETE_OLD_BILLINGS);
            connection.commit();
            return affectedRows;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            connection.rollback();
            return -1;
        } finally {
            if(connection != null) {
                connection.setAutoCommit(true);
                //connection.close();
            }
        }
    }

    public PostgreSQLBillingDao(Connection connection) {
        super(connection);
    }
}
