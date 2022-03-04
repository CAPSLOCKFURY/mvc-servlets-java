package models.resources;

import models.base.SqlRow;
import models.base.SqlType;

public class TestPreparedStatementForm {
    @SqlRow(rowName = "field", type = SqlType.STRING)
    private String field;

    @SqlRow(rowName = "stringField", type = SqlType.STRING)
    private String field2;

    @SqlRow(rowName = "intField", type = SqlType.INT)
    private Integer intField;

    public TestPreparedStatementForm(String field, String field2, Integer intField) {
        this.field = field;
        this.field2 = field2;
        this.intField = intField;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }
}
