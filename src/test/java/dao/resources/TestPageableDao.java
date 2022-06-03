package dao.resources;

import dao.dao.base.PageableAbstractDao;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestPageableDao extends PageableAbstractDao {

    public <T> List<T> abstractGetAll(String sql, Class<T> model, Pageable pageable) throws SQLException {
        return getAll(sql, model, pageable);
    }

    public <T, P> List<T> abstractGetAllByParams(String sql, P params, Class<T> model, Pageable pageable) throws SQLException {
        return getAllByParams(sql, params, model, pageable);
    }

    public TestPageableDao(Connection connection) {
        super(connection);
    }


}
