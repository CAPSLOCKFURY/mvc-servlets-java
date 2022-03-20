package dao.dao.base;

import exceptions.db.DaoException;
import models.base.PreparedStatementMapper;
import models.base.SqlMapper;

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
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<P> mapper = new PreparedStatementMapper<>(params, stmt);
        mapper.mapToPreparedStatement();
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
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<P> preparedStatementMapper = new PreparedStatementMapper<>(params, stmt);
        preparedStatementMapper.mapToPreparedStatement();
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

    /**
     * Creates new entity
     * @param connection Connection to the database
     * @param sql Sql insert query
     * @param form  Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return true if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean createEntity(Connection connection, String sql, F form) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<F> statementMapper = new PreparedStatementMapper<>(form, stmt);
        statementMapper.mapToPreparedStatement();
        return stmt.executeUpdate() == 1;
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
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        PreparedStatementMapper<F> statementMapper = new PreparedStatementMapper<>(form, stmt);
        statementMapper.mapToPreparedStatement();
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getLong(1);
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
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<F> preparedStatementMapper = new PreparedStatementMapper<>(form, stmt);
        preparedStatementMapper.mapToPreparedStatement();
        int numberOfPlaceholders = (int) sql.chars().filter(ch -> ch == '?').count();
        stmt.setLong(numberOfPlaceholders, id);
        return stmt.executeUpdate() == 1;
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
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<F> preparedStatementMapper = new PreparedStatementMapper<>(form, stmt);
        preparedStatementMapper.mapToPreparedStatement();
        return stmt.executeUpdate() == 1;
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
}
