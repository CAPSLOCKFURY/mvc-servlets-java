package models;

import forms.RegisterForm;
import models.base.SqlRow;
import models.base.SqlType;

public class User {

    @SqlRow(rowName = "id", type = SqlType.INT)
    private Integer id;

    @SqlRow(rowName = "login", type = SqlType.STRING)
    private String login;

    @SqlRow(rowName = "email", type = SqlType.STRING)
    private String email;

    public User(RegisterForm form){
        login = form.getLogin();
        email = form.getEmail();
    }

    public User(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
