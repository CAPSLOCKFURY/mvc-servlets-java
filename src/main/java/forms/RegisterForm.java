package forms;

import forms.base.*;

import java.util.regex.Pattern;

public class RegisterForm extends Form {
    private final static Pattern loginPattern = Pattern.compile("[a-zA-Z_0-9-]+");
    private final static Pattern emailPattern = Pattern.compile("[a-z0-9.]+@[a-z]+(.com|.net|.ukr|.ru|.ua)");
    private final static Pattern passwordPattern = Pattern.compile("[a-zA-Z_0-9-]+");

    @HtmlInput(type = InputType.TEXT, placeholder = "Login")
    private final String login;
    @HtmlInput(type = InputType.TEXT, placeholder = "Email")
    private final String email;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Password")
    private final String password;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Repeat Password")
    private final String repeatPassword;

    public RegisterForm(String login, String email, String password, String repeatPassword){
        this.login = login;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public boolean validate(){
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
}
