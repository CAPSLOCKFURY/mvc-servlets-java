package dao.resources;

import dao.dao.AbstractDao;

import java.sql.*;
import java.util.List;

public class TestDao extends AbstractDao {

    public <T> List<T> abstractGetAll(Connection connection, String sql, Class<T> model) throws SQLException {
        return getAll(connection, sql, model);
    }

    public <T> T abstractGetOneById(Connection connection, String sql, Long id, Class<T> model) throws SQLException{
        return getOneById(connection, sql, id, model);
    }

    public <T, P> T abstractGetOneByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException {
        return getOneByParams(connection, sql, params, model);
    }

    public <T, P> List<T> abstractGetAllByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException{
        return getAllByParams(connection, sql, params, model);
    }

    public <F> boolean abstractCreateEntity(Connection connection, String sql, F form) throws SQLException{
        return createEntity(connection, sql, form);
    }

    public <F> long abstractCreateEntityAndGetId(Connection connection, String sql, F form) throws SQLException{
        return createEntityAndGetId(connection, sql, form);
    }

    public <F> boolean abstractUpdateEntityById(Connection connection, String sql, F form, Long id) throws SQLException {
        return updateEntityById(connection, sql, form, id);
    }

    public <F> boolean abstractUpdateEntity(Connection connection, String sql, F form) throws SQLException {
        return updateEntity(connection, sql, form);
    }


}
