package models.enums;

public enum RoomOrdering {
    ID("id"), PRICE("price"), STATUS("status"), CAPACITY("capacity"), CLASS("class");

    private final String colName;

    RoomOrdering(String colName){
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public static RoomOrdering valueOfOrDefault(String value){
        try{
            if(value == null){
                throw new IllegalArgumentException();
            }
            return RoomOrdering.valueOf(value);
        } catch (IllegalArgumentException iag){
            return RoomOrdering.ID;
        }
    }
}
