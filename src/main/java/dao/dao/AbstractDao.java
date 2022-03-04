package dao.dao;

import exceptions.db.DaoException;
import forms.base.Form;
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
                T item = model.getConstructor().newInstance();
                SqlMapper<T> mapper = new SqlMapper<>(item);
                mapper.mapFromResultSet(rs);
                result.add(item);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new DaoException();
            }
        }
        return result;
    }

    public <T> T getOneById(Connection connection, String sql, int id, Class<T> model) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        try{
            T item = model.getConstructor().newInstance();
            while (rs.next()){
                SqlMapper<T> mapper = new SqlMapper<>(item);
                mapper.mapFromResultSet(rs);
            }
            return item;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }
        throw new DaoException();
    }

    public boolean createEntity(Connection connection, String sql, Form form) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement(sql);
        PreparedStatementMapper<Form> statementMapper = new PreparedStatementMapper<>(form, stmt);
        statementMapper.mapToPreparedStatement();
        return stmt.executeUpdate() == 1;
    }
}
