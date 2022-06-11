package models.dto;

import models.base.SqlColumn;
import models.base.SqlType;

import java.time.LocalDate;

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
    private LocalDate checkInDate;

    @SqlColumn(columnName = "check_out_date", type = SqlType.DATE)
    private LocalDate checkOutDate;

    @SqlColumn(columnName = "room_id", type = SqlType.LONG)
    private Long roomId;

    @SqlColumn(columnName = "login", type = SqlType.STRING)
    private String login;

    @SqlColumn(columnName = "email", type = SqlType.STRING)
    private String email;

    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;

    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;

    @SqlColumn(columnName = "manager_comment", type = SqlType.STRING)
    private String managerComment;

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

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
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

    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(String managerComment) {
        this.managerComment = managerComment;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
