package forms;

import forms.base.*;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterForm extends Form {
    private final static Pattern loginPattern = Pattern.compile("[a-zA-Z_0-9-]+");
    private final static Pattern emailPattern = Pattern.compile("[a-z0-9.]+@[a-z]+(.com|.net|.ukr|.ru|.ua)");
    private final static Pattern passwordPattern = Pattern.compile("[a-zA-Z_0-9-]+");

    @HtmlInput(type = InputType.TEXT, placeholder = "Login", localizedPlaceholder = "login")
    private String login;
    @HtmlInput(type = InputType.TEXT, placeholder = "Email", localizedPlaceholder = "email")
    private String email;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Password", localizedPlaceholder = "password")
    private String password;
    @HtmlInput(type = InputType.PASSWORD, placeholder = "Repeat Password", localizedPlaceholder = "repeatPassword")
    private String repeatPassword;

    @Override
    public boolean validate(){
        ResourceBundle formsBundle = ResourceBundle.getBundle("forms", locale);
        //TODO null checks
        if(!password.equals(repeatPassword)){
            errors.add(formsBundle.getString("errors.repeatPassword"));
        }
        if(!loginPattern.matcher(login).matches()){
            errors.add(formsBundle.getString("errors.loginRegex"));
        }
        if(!emailPattern.matcher(email).matches()){
            errors.add(formsBundle.getString("errors.emailRegex"));
        }
        if(!passwordPattern.matcher(password).matches()){
            errors.add(formsBundle.getString("errors.passwordRegex"));
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
