package models.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public void mapToPreparedStatement(){
        AtomicInteger stmtCursor = new AtomicInteger(1);
        Arrays.stream(form.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(SqlRow.class))
                .forEach(f -> {
                    if(ignoreFields.contains(f.getName())){
                       return;
                    }
                    SqlRow sqlRow = f.getAnnotation(SqlRow.class);
                    if(sqlRow.type().getTypeClass() == Integer.class){
                        try {
                            Integer value = (Integer) getGetterMethod(f.getName()).invoke(form);
                            stmt.setInt(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if(sqlRow.type().getTypeClass() == String.class){
                        try {
                            String value = (String) getGetterMethod(f.getName()).invoke(form);
                            stmt.setString(stmtCursor.getAndIncrement(), value);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                });
    }

    private Method getGetterMethod(String fieldName) throws NoSuchMethodException {
        return form.getClass().getDeclaredMethod("get".concat(capitalize(fieldName)));
    }
}
