package forms;

import forms.base.*;

import java.util.regex.Pattern;

public class RegisterForm extends Form {
    private final static Pattern loginPattern = Pattern.compile("[a-zA-Z_0-9-]+");
    private final static Pattern emailPattern = Pattern.compile("[a-z0-9.]+@[a-z]+(.com|.net|.ukr|.ru|.ua)");
    private final static Pattern passwordPattern = Pattern.compile("[a-zA-Z_0-9-]+");

    @HtmlInput(type = InputType.TEXT, placeholder = "Login")
    private String login;
    @HtmlInput(type = InputType.TEXT, placeholder = "Email")
    private String email;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Password")
    private String password;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Repeat Password")
    private String repeatPassword;

    @Override
    public boolean validate(){
        //TODO null checks
        if(!password.equals(repeatPassword)){
            errors.add("Password mismatch");
        }
        if(!loginPattern.matcher(login).matches()){
            errors.add("Login does not match regex");
        }
        if(!emailPattern.matcher(email).matches()){
            errors.add("Email does not match regex");
        }
        if(!passwordPattern.matcher(password).matches()){
            errors.add("Password does not match regex");
        }
        return errors.size() == 0;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
