package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Date;

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
    private String class_name;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private java.sql.Date check_out_date;

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

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }
}
