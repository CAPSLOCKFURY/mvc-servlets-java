package dao.dao;

import models.base.ordering.Orderable;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderableAbstractDao extends PageableAbstractDao {

    protected final <T> List<T> getAll(Connection connection, String sql, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException{
        sql = orderable.orderQuery(sql);
        return getAll(connection, sql, model, pageable);
    }

    protected final  <T, P> List<T> getAllByParams(Connection connection, String sql, P params, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException{
        sql = orderable.orderQuery(sql);
        return getAllByParams(connection, sql, params, model, pageable);
    }

}
