package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Date;

public class AdminRoomRequestDTO {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;
    @SqlColumn(columnName = "capacity", type = SqlType.INT)
    private Integer capacity;
    @SqlColumn(columnName = "class_name", type = SqlType.STRING)
    private String className;
    @SqlColumn(columnName = "comment", type = SqlType.STRING)
    private String comment;
    @SqlColumn(columnName = "status", type = SqlType.STRING)
    private String status;
    @SqlColumn(columnName = "check_in_date", type = SqlType.DATE)
    private java.sql.Date checkInDate;
    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private java.sql.Date checkOutDate;
    @SqlColumn(columnName = "login", type = SqlType.STRING)
    private String login;
    @SqlColumn(columnName = "email", type = SqlType.STRING)
    private String email;
    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;
    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
