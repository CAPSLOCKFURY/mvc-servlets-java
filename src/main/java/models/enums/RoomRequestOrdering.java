package models.enums;

public enum RoomRequestOrdering {
    ID("id"), CHECK_IN_DATE("check_in_date"), CHECK_OUT_DATE("check_out_date"), CAPACITY("capacity");

    private final String colName;

    RoomRequestOrdering(String colName){
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public static RoomRequestOrdering valueOfOrDefault(String value){
        try{
            if(value == null){
                throw new IllegalArgumentException();
            }
            return RoomRequestOrdering.valueOf(value);
        } catch (IllegalArgumentException iag){
            return RoomRequestOrdering.ID;
        }
    }
}
