package dao.resources.models;

import models.base.SqlColumn;
import models.base.SqlType;

public class DaoTestModel {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "name", type = SqlType.STRING)
    private String name;

    @SqlColumn(columnName = "age", type = SqlType.INT)
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
