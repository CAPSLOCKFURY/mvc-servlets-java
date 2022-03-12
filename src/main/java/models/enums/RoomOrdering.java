package models.enums;

public enum RoomOrdering {
    ID("id"), PRICE("price"), STATUS("status"), CAPACITY("status");

    private final String colName;

    RoomOrdering(String colName){
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public static RoomOrdering valueOfOrDefault(String value){
        try{
            return RoomOrdering.valueOf(value);
        } catch (IllegalArgumentException iag){
            return RoomOrdering.ID;
        }
    }
}
