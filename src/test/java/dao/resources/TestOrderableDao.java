package dao.resources;

import dao.dao.OrderableAbstractDao;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestOrderableDao extends OrderableAbstractDao {

    public <T> List<T> abstractGetAll(Connection connection, String sql, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException {
        return getAll(connection, sql, model, orderable, pageable);
    }

    public <T, P> List<T> abstractGetAllByParams(Connection connection, String sql, P params, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException{
        return getAllByParams(connection, sql, params, model, orderable, pageable);
    }

}
