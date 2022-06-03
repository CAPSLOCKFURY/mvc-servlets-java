package dao.dao.base;

import models.base.ordering.Orderable;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.util.List;

/**
 * Base class for dao that can do ordering and paginating
 */
public abstract class OrderableAbstractDao extends PageableAbstractDao {

    protected final <T> List<T> getAll(String sql, Class<T> model, Orderable orderable, Pageable pageable) {
        sql = orderable.orderQuery(sql);
        return getAll(sql, model, pageable);
    }

    protected final  <T, P> List<T> getAllByParams(String sql, P params, Class<T> model, Orderable orderable, Pageable pageable) {
        sql = orderable.orderQuery(sql);
        return getAllByParams(sql, params, model, pageable);
    }

    protected final  <T> List<T> getAllByParams(String sql, Object[] params, Class<T> model, Orderable orderable, Pageable pageable) {
        sql = orderable.orderQuery(sql);
        return getAllByParams(sql, params, model, pageable);
    }

    public OrderableAbstractDao(Connection connection) {
        super(connection);
    }

}
