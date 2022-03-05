package models.resources;

import models.base.SqlColumn;
import models.base.SqlType;

public class TestPreparedStatementForm {
    @SqlColumn(columnName = "field", type = SqlType.STRING)
    private String field;

    @SqlColumn(columnName = "stringField", type = SqlType.STRING)
    private String field2;

    @SqlColumn(columnName = "intField", type = SqlType.INT)
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
