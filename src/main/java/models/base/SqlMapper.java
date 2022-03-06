package models.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import static utils.StringUtils.capitalize;

public class SqlMapper<T> {
    private T model;

    public SqlMapper(T model){
        this.model = model;
    }

    public void mapFromResultSet(ResultSet rs){
        Arrays.stream(model.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(SqlColumn.class))
                .forEach(f -> {
                    try {
                        SqlColumn sqlColumn = f.getAnnotation(SqlColumn.class);
                        Method setterMethod = model.getClass().getMethod("set".concat(capitalize(f.getName())), sqlColumn.type().getTypeClass());
                        if(sqlColumn.type().getTypeClass() == Integer.class){
                            setterMethod.invoke(model, getIntFromRs(rs, sqlColumn.columnName()));
                            return;
                        }
                        if(sqlColumn.type().getTypeClass() == String.class){
                            setterMethod.invoke(model, getStringFromRs(rs, sqlColumn.columnName()));
                            return;
                        }
                        if(sqlColumn.type().getTypeClass() == Long.class){
                            setterMethod.invoke(model, getLongFromRs(rs, sqlColumn.columnName()));
                            return;
                        }
                        if(sqlColumn.type().getTypeClass() == BigDecimal.class){
                            setterMethod.invoke(model, getDecimalFromRs(rs, sqlColumn.columnName()));
                            return;
                        }
                        if(sqlColumn.type().getTypeClass() == java.sql.Date.class){
                            setterMethod.invoke(model, getDateFromRs(rs, sqlColumn.columnName()));
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    private Integer getIntFromRs(ResultSet rs, String rowName){
        try {
            return rs.getInt(rowName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getStringFromRs(ResultSet rs, String rowName){
        try {
            return rs.getString(rowName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long getLongFromRs(ResultSet rs, String rowName){
        try {
            return rs.getLong(rowName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BigDecimal getDecimalFromRs(ResultSet rs, String colName){
        try {
            return rs.getBigDecimal(colName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private java.sql.Date getDateFromRs(ResultSet rs, String colName){
        try {
            return rs.getDate(colName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
