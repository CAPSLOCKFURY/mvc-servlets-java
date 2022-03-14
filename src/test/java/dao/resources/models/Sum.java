package dao.resources.models;


import models.base.SqlColumn;
import models.base.SqlType;

public class Sum {
    @SqlColumn(columnName = "sum", type = SqlType.INT)
    private Integer sum;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
