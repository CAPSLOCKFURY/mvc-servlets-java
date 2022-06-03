package dao.resources;

import dao.dao.base.AbstractDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestDao extends AbstractDao {

    public <T> List<T> abstractGetAll(String sql, Class<T> model) {
        return getAll(sql, model);
    }

    public <T> T abstractGetOneById(String sql, Long id, Class<T> model) {
        return getOneById(sql, id, model);
    }

    public <T, P> T abstractGetOneByParams(String sql, P params, Class<T> model) {
        return getOneByParams(sql, params, model);
    }

    public <T, P> List<T> abstractGetAllByParams(String sql, P params, Class<T> model) {
        return getAllByParams(sql, params, model);
    }

    public <F> boolean abstractCreateEntity(String sql, F form) {
        return createEntity(sql, form);
    }

    public <F> long abstractCreateEntityAndGetId(String sql, F form) {
        return createEntityAndGetId(sql, form);
    }

    public <F> boolean abstractUpdateEntityById(String sql, F form, Long id) throws SQLException {
        return updateEntityById(sql, form, id);
    }

    public <F> boolean abstractUpdateEntity(String sql, F form) throws SQLException {
        return updateEntity(sql, form);
    }

    public TestDao(Connection connection) {
        super(connection);
    }

}
