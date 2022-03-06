package models.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static utils.StringUtils.capitalize;

public class PreparedStatementMapper<T> {

    private T form;
    private PreparedStatement stmt;
    private Set<String> ignoreFields;

    public PreparedStatementMapper(T form, PreparedStatement stmt, String ...ignoreFields){
        this.form = form;
        this.stmt = stmt;
        this.ignoreFields = new HashSet<>(Arrays.asList(ignoreFields));
    }
    // TODO remove stmt from constructor and add it to this method
    public void mapToPreparedStatement(){
        AtomicInteger stmtCursor = new AtomicInteger(1);
        Arrays.stream(form.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(SqlColumn.class))
                .forEach(f -> {
                    if(ignoreFields.contains(f.getName())){
                       return;
                    }
                    SqlColumn sqlColumn = f.getAnnotation(SqlColumn.class);
                    if(sqlColumn.type().getTypeClass() == Integer.class){
                        try {
                            Integer value = (Integer) getGetterMethod(f.getName()).invoke(form);
                            stmt.setInt(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(sqlColumn.type().getTypeClass() == String.class){
                        try {
                            String value = (String) getGetterMethod(f.getName()).invoke(form);
                            stmt.setString(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(sqlColumn.type().getTypeClass() == Long.class){
                        try{
                            Long value = (Long) getGetterMethod(f.getName()).invoke(form);
                            stmt.setLong(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e){
                            e.printStackTrace();
                        }
                    }
                    if(sqlColumn.type().getTypeClass() == BigDecimal.class){
                        try{
                            BigDecimal value = (BigDecimal) getGetterMethod(f.getName()).invoke(form);
                            stmt.setBigDecimal(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(sqlColumn.type().getTypeClass() == java.sql.Date.class){
                        try{
                            java.sql.Date value = (java.sql.Date) getGetterMethod(f.getName()).invoke(form);
                            stmt.setDate(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                });
    }

    private Method getGetterMethod(String fieldName) throws NoSuchMethodException {
        Method getter = form.getClass().getDeclaredMethod("get".concat(capitalize(fieldName)));
        getter.setAccessible(true);
        return getter;
    }
}
