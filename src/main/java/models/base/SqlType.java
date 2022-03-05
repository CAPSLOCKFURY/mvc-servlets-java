package models.base;

public enum SqlType {
    INT(Integer.class), STRING(String.class), LONG(Long.class);

    private final Class<?> typeClass;

    SqlType(Class<?> typeClass){
        this.typeClass = typeClass;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }


}
