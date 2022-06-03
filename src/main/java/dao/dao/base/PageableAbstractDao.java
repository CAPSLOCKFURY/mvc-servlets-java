package dao.dao.base;

import models.base.pagination.Pageable;

import java.sql.Connection;
import java.util.List;

/**
 * Base dao which can do paginating
 */
public abstract class PageableAbstractDao extends AbstractDao {

    protected final <T> List<T> getAll(String sql, Class<T> model, Pageable pageable) {
        sql = pageable.paginateQuery(sql);
        return getAll(sql, model);
    }

    protected final <T, P> List<T> getAllByParams(String sql, P params, Class<T> model, Pageable pageable) {
        sql = pageable.paginateQuery(sql);
        return getAllByParams(sql, params, model);
    }

    protected final <T> List<T> getAllByParams(String sql, Object[] params, Class<T> model, Pageable pageable) {
        sql = pageable.paginateQuery(sql);
        return getAllByParams(sql, params, model);
    }

    public PageableAbstractDao(Connection connection) {
        super(connection);
    }
}
