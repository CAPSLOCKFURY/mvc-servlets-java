package models;

import forms.RegisterForm;

public class User {
    private int id;
    private String login;
    private String email;

    public User(RegisterForm form){
        login = form.getLogin();
        email = form.getEmail();
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }
}
