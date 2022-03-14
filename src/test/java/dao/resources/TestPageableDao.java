package dao.resources;

import dao.dao.PageableAbstractDao;
import models.base.pagination.Pageable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestPageableDao extends PageableAbstractDao {

    public <T> List<T> abstractGetAll(Connection connection, String sql, Class<T> model, Pageable pageable) throws SQLException {
        return getAll(connection, sql, model, pageable);
    }

    public <T, P> List<T> abstractGetAllByParams(Connection connection, String sql, P params, Class<T> model, Pageable pageable) throws SQLException {
        return getAllByParams(connection, sql, params, model, pageable);
    }

}
