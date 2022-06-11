package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.BillingDao;
import models.Billing;
import models.base.pagination.Pageable;
import models.dto.ExtendedBillingDTO;

import java.sql.Connection;
import java.util.List;

public class PostgreSQLBillingDao extends BillingDao {

    @Override
    public boolean createBilling(Billing billing) {
        return createEntity(SqlQueries.Billing.INSERT_BILLING,
                new Object[]{billing.getRequestId(), billing.getPrice(), billing.getPaid(), billing.getRoomRegistryId()});
    }

    @Override
    public boolean updateBilling(Billing billing) {
        return updateEntityById(SqlQueries.Billing.UPDATE_BILLING,
                new Object[]{billing.getRequestId(), billing.getPrice(), billing.getPayEndDate(), billing.getPaid(), billing.getRoomRegistryId()}, billing.getId());
    }

    @Override
    public Billing getBillingById(Long billingId) {
        return getOneById(SqlQueries.Billing.GET_BILLING_BY_ID, billingId, Billing.class);
    }

    @Override
    public ExtendedBillingDTO getExtendedBillingById(Long billingId) {
        return getOneById(SqlQueries.Billing.GET_EXTENDED_BILLING_BY_ID, billingId, ExtendedBillingDTO.class);
    }

    @Override
    public List<Billing> getAllBillingsByUserId(Long userId, Pageable pageable) {
        return getAllByParams(SqlQueries.Billing.FIND_ALL_BILLING_BY_USER_ID, new Object[]{userId}, Billing.class, pageable);
    }

    @Override
    public int deleteOldBillings() {
        updatePlain(SqlQueries.Billing.DELETE_ROOM_REQUESTS_CONNECTED_TO_OLD_BILLING);
        updatePlain(SqlQueries.Billing.DELETE_ROOM_REGISTRIES_CONNECTED_TO_OLD_BILLING);
        return updatePlain(SqlQueries.Billing.DELETE_OLD_BILLINGS);
    }

    public PostgreSQLBillingDao(Connection connection) {
        super(connection);
    }
}
