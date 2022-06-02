package models;

import forms.RegisterForm;
import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;

public class User {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "login", type = SqlType.STRING)
    private String login;

    @SqlColumn(columnName = "email", type = SqlType.STRING)
    private String email;

    @SqlColumn(columnName = "password", type = SqlType.STRING)
    private String password;

    @SqlColumn(columnName = "first_name", type = SqlType.STRING)
    private String firstName;

    @SqlColumn(columnName = "last_name", type = SqlType.STRING)
    private String lastName;

    @SqlColumn(columnName = "role", type = SqlType.INT)
    private Integer role;

    @SqlColumn(columnName = "balance", type = SqlType.DECIMAL)
    private BigDecimal balance;


    public User(RegisterForm form){
        login = form.getLogin();
        email = form.getEmail();
        firstName = form.getFirstName();
        lastName = form.getLastName();
        password = form.getPassword();
        role = 1;
        balance = BigDecimal.ZERO;
    }

    public User(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
