package dao.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BillingDao extends AbstractDao {

    public abstract boolean insertBilling(Connection connection, Long requestId, BigDecimal price) throws SQLException;

}
