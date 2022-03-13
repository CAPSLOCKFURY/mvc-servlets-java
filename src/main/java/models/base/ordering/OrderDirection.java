package models.base.ordering;

public enum OrderDirection {
    ASC, DESC;

    public static OrderDirection valueOfOrDefault(String value){
        if(value == null){
            return OrderDirection.ASC;
        }
        if(value.equals("ASC") || value.equals("DESC")){
            return OrderDirection.valueOf(value);
        }
        return OrderDirection.ASC;
    }
}
