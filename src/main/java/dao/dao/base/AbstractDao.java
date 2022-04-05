package dao.dao.base;

import exceptions.db.DaoException;
import models.base.mappers.PreparedStatementArrayMapper;
import models.base.mappers.PreparedStatementClassMapper;
import models.base.mappers.PreparedStatementMapper;
import models.base.mappers.SqlMapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Base dao util class which contains repeating logic for jdbc
 */
public abstract class AbstractDao {

    /**
     * Finds all entities of given type
     * @param connection Connection to the database
     * @param sql Sql query to find all
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return List of all found models
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T> List<T> getAll(Connection connection, String sql, Class<T> model) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<T> result = new LinkedList<>();
        while (rs.next()){
            try {
                T entity = model.getConstructor().newInstance();
                SqlMapper<T> mapper = new SqlMapper<>(entity);
                mapper.mapFromResultSet(rs);
                result.add(entity);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DaoException();
            }
        }
        return result;
    }

    /**
     * Finds entity by its id, if entity is not found it will return entity which corresponds to entities basic no-arg constructor
     * @param connection Connection to the database
     * @param sql Sql query to find entity by id <p>Note: sql query should contain only one wildcard which should be id</p>
     * @param id Id which will be mapped to sql
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return Found model
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T> T getOneById(Connection connection, String sql, Long id, Class<T> model) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        try{
            //TODO put duplicated code to another method
            T entity = model.getConstructor().newInstance();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                SqlMapper<T> mapper = new SqlMapper<>(entity);
                mapper.mapFromResultSet(rs);
            }
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
            throw new DaoException();
        }
    }


    /**
     * Finds entity by params, if entity is not found it will return entity which corresponds to entities basic no-arg constructor
     * @param connection Connection to the database
     * @param sql Sql query to find entity by one or more params
     * @param params Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return Found model
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T, P> T getOneByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException {
        return getOneByParams(connection, sql, params, model, new PreparedStatementClassMapper<>(params));
    }

    protected final <T> T getOneByParams(Connection connection, String sql, Object[] params, Class<T> model) throws SQLException{
        return getOneByParams(connection, sql, params, model, new PreparedStatementArrayMapper(params));
    }

    /**
     *  Finds all entities by given params
     * @param connection Connection to the database
     * @param sql Sql query to find entity by one or more params
     * @param params Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return List of found entities
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T, P> List<T> getAllByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException{
        return getAllByParams(connection, sql, params, model, new PreparedStatementClassMapper<>(params));
    }

    protected final <T> List<T> getAllByParams(Connection connection, String sql, Object[] params, Class<T> model) throws SQLException{
        return getAllByParams(connection, sql, params, model, new PreparedStatementArrayMapper(params));
    }

    /**
     * Creates new entity
     * @param connection Connection to the database
     * @param sql Sql insert query
     * @param form  Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return true if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean createEntity(Connection connection, String sql, F form) throws SQLException{
        return createEntity(connection, sql, form, new PreparedStatementClassMapper<>(form));
    }

    protected final boolean createEntity(Connection connection, String sql, Object[] form) throws SQLException{
        return createEntity(connection, sql, form, new PreparedStatementArrayMapper(form));
    }

    /**
     * Creates new entity and returns its id
     * @param connection Connection to the database
     * @param sql Sql insert query
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return id of newly created entity
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> long createEntityAndGetId(Connection connection, String sql, F form) throws SQLException{
        return createEntityAndGetId(connection, sql, form, new PreparedStatementClassMapper<>(form));
    }

    protected final <F> long createEntityAndGetId(Connection connection, String sql, Object[] form) throws SQLException{
        return createEntityAndGetId(connection, sql, form, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entity with given params and id
     * @param connection Connection to the database
     * @param sql Sql update query, last wildcard should be id
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param id Id of entity to update
     * @return True if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean updateEntityById(Connection connection, String sql, F form, Long id) throws SQLException {
        return updateEntityById(connection, sql, form, id, new PreparedStatementClassMapper<>(form));
    }

    protected final <F> boolean updateEntityById(Connection connection, String sql, Object[] form, Long id) throws SQLException {
        return updateEntityById(connection, sql, form, id, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entity by given params
     * @param connection Connection to the database
     * @param sql Sql update query
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return True if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean updateEntity(Connection connection, String sql, F form) throws SQLException {
        return updateEntity(connection, sql, form, new PreparedStatementClassMapper<>(form));
    }

    protected final <F> boolean updateEntity(Connection connection, String sql, Object[] form) throws SQLException {
        return updateEntity(connection, sql, form, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entities with no given params
     * @param connection Connection to the database
     * @param sql Sql update query without wildcards
     * @return number of affected rows
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final int updatePlain(Connection connection, String sql) throws SQLException{
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }

    private <T, P> T getOneByParams(Connection connection, String sql, P params, Class<T> model, PreparedStatementMapper mapper) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        mapper.mapToPreparedStatement(stmt);
        try{
            T entity = model.getConstructor().newInstance();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                SqlMapper<T> sqlMapper = new SqlMapper<>(entity);
                sqlMapper.mapFromResultSet(rs);
            }
            return entity;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    private <T, P> List<T> getAllByParams(Connection connection, String sql, P params, Class<T> model, PreparedStatementMapper mapper) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        mapper.mapToPreparedStatement(stmt);
        try{
            ResultSet rs = stmt.executeQuery();
            List<T> result = new LinkedList<>();
            while (rs.next()){
                T entity = model.getConstructor().newInstance();
                SqlMapper<T> sqlMapper = new SqlMapper<>(entity);
                sqlMapper.mapFromResultSet(rs);
                result.add(entity);
            }
            return result;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

     private <F> boolean createEntity(Connection connection, String sql, F form, PreparedStatementMapper mapper) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        mapper.mapToPreparedStatement(stmt);
        return stmt.executeUpdate() == 1;
    }

    private <F> long createEntityAndGetId(Connection connection, String sql, F form, PreparedStatementMapper mapper) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        mapper.mapToPreparedStatement(stmt);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getLong(1);
    }

    private <F> boolean updateEntityById(Connection connection, String sql, F form, Long id, PreparedStatementMapper mapper) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        mapper.mapToPreparedStatement(stmt);
        int numberOfPlaceholders = (int) sql.chars().filter(ch -> ch == '?').count();
        stmt.setLong(numberOfPlaceholders, id);
        return stmt.executeUpdate() == 1;
    }

    private <F> boolean updateEntity(Connection connection, String sql, F form, PreparedStatementMapper mapper) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        mapper.mapToPreparedStatement(stmt);
        return stmt.executeUpdate() == 1;
    }

}
