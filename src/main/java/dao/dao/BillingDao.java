package dao.dao;

import models.Billing;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BillingDao extends AbstractDao {

    public abstract boolean insertBilling(Connection connection, Long requestId, BigDecimal price) throws SQLException;

    public abstract List<Billing> getAllBillingsByUserId(Long userId) throws SQLException;
}
