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
                .filter(f -> f.isAnnotationPresent(SqlRow.class))
                .forEach(f -> {
                    try {
                        SqlRow sqlRow = f.getAnnotation(SqlRow.class);
                        Method setterMethod = model.getClass().getMethod("set".concat(capitalize(f.getName())), sqlRow.type().getTypeClass());
                        if(sqlRow.type().getTypeClass() == Integer.class){
                            setterMethod.invoke(model, getIntFromRs(rs, sqlRow.rowName()));
                        }
                        if(sqlRow.type().getTypeClass() == String.class){
                            setterMethod.invoke(model, getStringFromRs(rs, sqlRow.rowName()));
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

}
