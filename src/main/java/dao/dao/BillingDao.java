package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.Billing;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BillingDao extends OrderableAbstractDao {

    public abstract boolean insertBilling(Connection connection, Long requestId, BigDecimal price, Long RoomRegistryId) throws SQLException;

    public abstract List<Billing> getAllBillingsByUserId(Long userId, Pageable pageable) throws SQLException;

    public abstract ExtendedBillingDTO getBillingById(Long billingId) throws SQLException;

    public abstract boolean payBilling(Long userId, ExtendedBillingDTO billing) throws SQLException;

    public abstract int deleteOldBillings() throws SQLException;
}
