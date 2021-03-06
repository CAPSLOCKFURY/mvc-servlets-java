package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RoomExtendedInfo {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "name", type = SqlType.STRING)
    private String name;

    @SqlColumn(columnName = "number", type = SqlType.INT)
    private Integer number;

    @SqlColumn(columnName = "status", type = SqlType.STRING)
    private String status;

    @SqlColumn(columnName = "price", type = SqlType.DECIMAL)
    private BigDecimal price;

    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    private Integer capacity;

    @SqlColumn(columnName = "class_name", type = SqlType.STRING)
    private String className;

    private List<RoomDate> dates = new LinkedList<>();

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

    public List<RoomDate> getDates() {
        return dates;
    }

    public void setDates(List<RoomDate> dates) {
        this.dates = dates;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
