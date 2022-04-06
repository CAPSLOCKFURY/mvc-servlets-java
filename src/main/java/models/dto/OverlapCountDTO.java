package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

public class OverlapCountDTO {

    @SqlColumn(columnName = "cnt", type = SqlType.INT)
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
