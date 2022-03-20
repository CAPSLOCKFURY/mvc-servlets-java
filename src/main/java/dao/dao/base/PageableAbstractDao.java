package dao.dao.base;

import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PageableAbstractDao extends AbstractDao {

    protected final <T> List<T> getAll(Connection connection, String sql, Class<T> model, Pageable pageable) throws SQLException {
        sql = pageable.paginateQuery(sql);
        return getAll(connection, sql, model);
    }

    protected final  <T, P> List<T> getAllByParams(Connection connection, String sql, P params, Class<T> model, Pageable pageable) throws SQLException {
        sql = pageable.paginateQuery(sql);
        return getAllByParams(connection, sql, params, model);
    }
}
