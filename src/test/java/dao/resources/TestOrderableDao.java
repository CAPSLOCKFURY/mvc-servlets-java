package dao.resources;

import dao.dao.base.OrderableAbstractDao;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestOrderableDao extends OrderableAbstractDao {

    public <T> List<T> abstractGetAll(String sql, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException {
        return getAll(sql, model, orderable, pageable);
    }

    public <T, P> List<T> abstractGetAllByParams(String sql, P params, Class<T> model, Orderable orderable, Pageable pageable) throws SQLException{
        return getAllByParams(sql, params, model, orderable, pageable);
    }

    public TestOrderableDao(Connection connection) {
        super(connection);
    }

}
