package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.Billing;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BillingDao extends OrderableAbstractDao {

    public abstract boolean createBilling(Billing billing);

    public abstract boolean updateBilling(Billing billing);

    public abstract List<Billing> getAllBillingsByUserId(Long userId, Pageable pageable);

    public abstract ExtendedBillingDTO getExtendedBillingById(Long billingId);

    public abstract Billing getBillingById(Long billingId);

    /**
     * Should be used for deleting old billings from the database
     * @return number of affected rows
     */
    public abstract int deleteOldBillings() throws SQLException;

    public BillingDao(Connection connection) {
        super(connection);
    }
}
