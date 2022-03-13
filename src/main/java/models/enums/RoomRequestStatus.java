package models.enums;

public enum RoomRequestStatus {
    CLOSED("closed"), PAID("paid"), AWAITING_PAYMENT("awaiting payment"), AWAITING_CONFIRMATION("awaiting confirmation"), AWAITING("awaiting");

    private String colName;

    RoomRequestStatus(String colName){
        this.colName = colName;
    }

    public String getColName() {
        return colName;
    }

    public static RoomRequestStatus valueOfOrDefault(String value){
        try {
            return RoomRequestStatus.valueOf(value);
        } catch (IllegalArgumentException iag){
            return RoomRequestStatus.AWAITING;
        }
    }
}
