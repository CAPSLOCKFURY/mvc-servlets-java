package models.resources;

import models.base.SqlColumn;
import models.base.SqlType;

public class TestModel {

    @SqlColumn(columnName = "field", type = SqlType.STRING)
    public String field;

    @SqlColumn(columnName = "intField", type = SqlType.INT)
    public Integer intField;

    @SqlColumn(columnName = "stringField", type = SqlType.STRING)
    public String field1;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "field='" + field + '\'' +
                ", intField='" + intField + '\'' +
                ", field1='" + field1 + '\'' +
                '}';
    }
}
