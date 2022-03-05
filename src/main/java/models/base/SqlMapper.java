package models.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
                            setterMethod.invoke(model, getIntFromRs(rs, sqlColumn.rowName()));
                        }
                        if(sqlColumn.type().getTypeClass() == String.class){
                            setterMethod.invoke(model, getStringFromRs(rs, sqlColumn.rowName()));
                        }
                        if(sqlColumn.type().getTypeClass() == Long.class){
                            setterMethod.invoke(model, getLongFromRs(rs, sqlColumn.rowName()));
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

}
