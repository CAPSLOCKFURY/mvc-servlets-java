package dao.dao;

import exceptions.db.DaoException;
import models.base.PreparedStatementMapper;
import models.base.SqlMapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDao {

    public <T> List<T> getAll(Connection connection, String sql, Class<T> model) throws SQLException {
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

    public <T> T getOneById(Connection connection, String sql, Long id, Class<T> model) throws SQLException{
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

    public <T, P> T getOneByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException {
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

    public <T, P> List<T> getAllByParams(Connection connection, String sql, P params, Class<T> model) throws SQLException{
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

    public <F> boolean createEntity(Connection connection, String sql, F form) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<F> statementMapper = new PreparedStatementMapper<>(form, stmt);
        statementMapper.mapToPreparedStatement();
        return stmt.executeUpdate() == 1;
    }

    public <F> long createEntityAndGetId(Connection connection, String sql, F form) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        PreparedStatementMapper<F> statementMapper = new PreparedStatementMapper<>(form, stmt);
        statementMapper.mapToPreparedStatement();
        //TODO add check for successful update
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getLong(1);
    }

    public <F> boolean updateEntityById(Connection connection, String sql, F form, Long id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<F> preparedStatementMapper = new PreparedStatementMapper<>(form, stmt);
        preparedStatementMapper.mapToPreparedStatement();
        int numberOfPlaceholders = (int) sql.chars().filter(ch -> ch == '?').count();
        stmt.setLong(numberOfPlaceholders, id);
        return stmt.executeUpdate() == 1;
    }
}
