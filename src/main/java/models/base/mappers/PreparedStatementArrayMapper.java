package models.base.mappers;

import exceptions.db.DaoException;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class PreparedStatementArrayMapper implements PreparedStatementMapper {

    private final Object[] params;

    public PreparedStatementArrayMapper( Object ...params){
        this.params = params;
    }

    @Override
    public void mapToPreparedStatement(PreparedStatement stmt) {
        AtomicInteger stmtCursor = new AtomicInteger(1);
        for(Object o : params) {
            if(o == null){
                try {
                    stmt.setObject(stmtCursor.getAndIncrement(), null);
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                    throw new DaoException();
                }
                continue;
            }
            if (o.getClass() == SqlType.INT.getTypeClass()) {
                try {
                    stmt.setInt(stmtCursor.getAndIncrement(), (Integer) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            if (o.getClass() == SqlType.STRING.getTypeClass()) {
                try {
                    stmt.setString(stmtCursor.getAndIncrement(), (String) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            if (o.getClass() == SqlType.LONG.getTypeClass()) {
                try {
                    stmt.setLong(stmtCursor.getAndIncrement(), (Long) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            if (o.getClass() == SqlType.DECIMAL.getTypeClass()) {
                try {
                    stmt.setBigDecimal(stmtCursor.getAndIncrement(), (BigDecimal) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            if (o.getClass() == SqlType.DATE.getTypeClass()) {
                try {
                    stmt.setDate(stmtCursor.getAndIncrement(), (java.sql.Date) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
            if (o.getClass() == SqlType.BOOLEAN.getTypeClass()) {
                try {
                    stmt.setBoolean(stmtCursor.getAndIncrement(), (Boolean) o);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DaoException();
                }
            }
        }
    }

}
