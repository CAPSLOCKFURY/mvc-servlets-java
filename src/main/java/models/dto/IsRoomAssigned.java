package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

public class IsRoomAssigned {

    @SqlColumn(type = SqlType.BOOLEAN, columnName = "assigned")
    private Boolean assigned;

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }
}
