package dao.dao.base;

import exceptions.db.DaoException;
import models.base.mappers.PreparedStatementArrayMapper;
import models.base.mappers.PreparedStatementClassMapper;
import models.base.mappers.PreparedStatementMapper;
import models.base.mappers.SqlModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Base dao util class which contains repeating logic for jdbc
 */
public abstract class AbstractDao implements AutoCloseable {

    protected Connection connection;

    public AbstractDao(Connection connection) {
        if(connection == null){
            throw new IllegalArgumentException("Connection in dao can't be null");
        }
        this.connection = connection;
    }

    public final void close(){
        try {
            if(connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Finds all entities of given type
     * @param sql Sql query to find all
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return List of all found models
     * @throws DaoException on SqlException
     */
    protected final <T> List<T> getAll(String sql, Class<T> model) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<T> result = new LinkedList<>();
            while (rs.next()) {
                try {
                    T entity = model.getConstructor().newInstance();
                    SqlModelMapper<T> mapper = new SqlModelMapper<>(entity);
                    mapper.mapFromResultSet(rs);
                    result.add(entity);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            return result;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    /**
     * Finds entity by its id, if entity is not found it will return entity which corresponds to entities basic no-arg constructor
     * @param sql Sql query to find entity by id <p>Note: sql query should contain only one wildcard which should be id</p>
     * @param id Id which will be mapped to sql
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return Found model
     * @throws DaoException on SqlException
     */
    protected final <T> T getOneById(String sql, Long id, Class<T> model) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            try {
                //TODO put duplicated code to another method
                T entity = model.getConstructor().newInstance();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    SqlModelMapper<T> mapper = new SqlModelMapper<>(entity);
                    mapper.mapFromResultSet(rs);
                }
                return entity;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DaoException();
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }


    /**
     * Finds entity by params, if entity is not found it will return entity which corresponds to entities basic no-arg constructor
     * @param sql Sql query to find entity by one or more params
     * @param params Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return Found model
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T, P> T getOneByParams(String sql, P params, Class<T> model) {
        return getOneByParams(sql, model, new PreparedStatementClassMapper<>(params));
    }

    protected final <T> T getOneByParams(String sql, Object[] params, Class<T> model) {
        return getOneByParams(sql, model, new PreparedStatementArrayMapper(params));
    }

    /**
     *  Finds all entities by given params
     * @param sql Sql query to find entity by one or more params
     * @param params Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param model Class of model to which ResultSet will be mapped to, should have no-arg constructor and {@link models.base.SqlColumn} annotations
     * @return List of found entities
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <T, P> List<T> getAllByParams(String sql, P params, Class<T> model) {
        return getAllByParams(sql, model, new PreparedStatementClassMapper<>(params));
    }

    protected final <T> List<T> getAllByParams(String sql, Object[] params, Class<T> model) {
        return getAllByParams(sql, model, new PreparedStatementArrayMapper(params));
    }

    /**
     * Creates new entity
     * @param sql Sql insert query
     * @param form  Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return true if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean createEntity(String sql, F form) {
        return createEntity(sql, new PreparedStatementClassMapper<>(form));
    }

    protected final boolean createEntity(String sql, Object[] form) {
        return createEntity(sql, new PreparedStatementArrayMapper(form));
    }

    /**
     * Creates new entity and returns its id
     * @param sql Sql insert query
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return id of newly created entity
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> long createEntityAndGetId(String sql, F form) {
        return createEntityAndGetId(sql, new PreparedStatementClassMapper<>(form));
    }

    protected final long createEntityAndGetId(String sql, Object[] form) {
        return createEntityAndGetId(sql, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entity with given params and id
     * @param sql Sql update query, last wildcard should be id
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @param id Id of entity to update
     * @return True if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean updateEntityById(String sql, F form, Long id) {
        return updateEntityById(sql, id, new PreparedStatementClassMapper<>(form));
    }

    protected final boolean updateEntityById(String sql, Object[] form, Long id) {
        return updateEntityById(sql, id, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entity by given params
     * @param sql Sql update query
     * @param form Any class which has {@link models.base.SqlColumn} annotation from which values will be got to map it to prepared stmt
     * @return True if number of affected rows == 1
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final <F> boolean updateEntity(String sql, F form) {
        return updateEntity(sql, new PreparedStatementClassMapper<>(form));
    }

    protected final boolean updateEntity(String sql, Object[] form) {
        return updateEntity(sql, new PreparedStatementArrayMapper(form));
    }

    /**
     * Updates entities with no given params
     * @param sql Sql update query without wildcards
     * @return number of affected rows
     * @throws SQLException this method does not handle sql exceptions
     */
    protected final int updatePlain(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    private <T> T getOneByParams(String sql, Class<T> model, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            mapper.mapToPreparedStatement(stmt);
            try {
                T entity = model.getConstructor().newInstance();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    SqlModelMapper<T> sqlModelMapper = new SqlModelMapper<>(entity);
                    sqlModelMapper.mapFromResultSet(rs);
                }
                return entity;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DaoException();
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    private <T> List<T> getAllByParams(String sql, Class<T> model, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            mapper.mapToPreparedStatement(stmt);
            try {
                ResultSet rs = stmt.executeQuery();
                List<T> result = new LinkedList<>();
                while (rs.next()) {
                    T entity = model.getConstructor().newInstance();
                    SqlModelMapper<T> sqlModelMapper = new SqlModelMapper<>(entity);
                    sqlModelMapper.mapFromResultSet(rs);
                    result.add(entity);
                }
                return result;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DaoException();
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

     private boolean createEntity(String sql, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            mapper.mapToPreparedStatement(stmt);
            return stmt.executeUpdate() == 1;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    private long createEntityAndGetId(String sql, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mapper.mapToPreparedStatement(stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    private boolean updateEntityById(String sql, Long id, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            mapper.mapToPreparedStatement(stmt);
            int numberOfPlaceholders = (int) sql.chars().filter(ch -> ch == '?').count();
            stmt.setLong(numberOfPlaceholders, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

    private boolean updateEntity(String sql, PreparedStatementMapper mapper) {
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            mapper.mapToPreparedStatement(stmt);
            return stmt.executeUpdate() == 1;
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new DaoException();
        }
    }

}
