package models.base.mappers;

import models.base.SqlColumn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static utils.StringUtils.capitalize;

/**
 * Class for mapping object to prepared statement
 * <p>
 *     Note: object from which statement will be mapped, should have getter methods for each of its fields annotated with {@link SqlColumn}
 * </p>
 */
public class PreparedStatementClassMapper<T> implements PreparedStatementMapper {

    private T form;
    private Set<String> ignoreFields;

    /**
     * @param form Object from which statement will be mapped, you should put {@link SqlColumn} annotation on fields that should be mapped, you can ignore {@link SqlColumn#columnName()}
     * @param ignoreFields name of fields that should be ignored at mapping
     */
    public PreparedStatementClassMapper(T form, String ...ignoreFields){
        this.form = form;
        this.ignoreFields = new HashSet<>(Arrays.asList(ignoreFields));
    }

    @Override
    public void mapToPreparedStatement(PreparedStatement stmt){
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
                            java.sql.Date value = null;
                            if(f.getType() == LocalDate.class){
                                value = java.sql.Date.valueOf((LocalDate) getGetterMethod(f.getName()).invoke(form));
                            }
                            if(f.getType() == java.sql.Date.class){
                                value = (java.sql.Date) getGetterMethod(f.getName()).invoke(form);
                            }
                            stmt.setDate(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e){
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(sqlColumn.type().getTypeClass() == Boolean.class){
                        try{
                            Boolean value = (Boolean) getGetterMethod(f.getName()).invoke(form);
                            stmt.setBoolean(stmtCursor.getAndIncrement(), value);
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
