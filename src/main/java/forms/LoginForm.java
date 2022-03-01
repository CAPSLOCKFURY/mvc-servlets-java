package forms;

import forms.base.Form;
import forms.base.HtmlInput;
import forms.base.InputType;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginForm extends Form {

    private final static Pattern loginPattern = Pattern.compile("[a-zA-Z_0-9-]+");
    private final static Pattern passwordPattern = Pattern.compile("[a-zA-Z_0-9-]+");


    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "login")
    private String login;
    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password")
    private String password;

    @Override
    public boolean validate() {
        ResourceBundle formBundle = ResourceBundle.getBundle("forms", locale);
        if(!loginPattern.matcher(login).matches()){
            errors.add(formBundle.getString("errors.loginRegex"));
        }
        if(!passwordPattern.matcher(password).matches()){
            errors.add(formBundle.getString("errors.passwordRegex"));
        }
        return errors.size() == 0;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
