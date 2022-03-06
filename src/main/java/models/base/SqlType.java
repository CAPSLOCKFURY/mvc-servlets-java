package models.base;

import java.math.BigDecimal;

public enum SqlType {
    INT(Integer.class), STRING(String.class), LONG(Long.class), DECIMAL(BigDecimal.class),
    DATE(java.sql.Date.class);

    private final Class<?> typeClass;

    SqlType(Class<?> typeClass){
        this.typeClass = typeClass;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }


}
