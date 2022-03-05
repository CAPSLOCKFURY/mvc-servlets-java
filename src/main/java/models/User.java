package models;

import forms.RegisterForm;
import models.base.SqlColumn;
import models.base.SqlType;

public class User {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "login", type = SqlType.STRING)
    private String login;

    @SqlColumn(columnName = "email", type = SqlType.STRING)
    private String email;

    public User(RegisterForm form){
        login = form.getLogin();
        email = form.getEmail();
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
}
