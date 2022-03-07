package forms;

import forms.base.Form;
import forms.base.InputType;
import forms.base.annotations.HtmlInput;
import models.base.SqlColumn;
import models.base.SqlType;

import java.util.regex.Pattern;

public class LoginForm extends Form {

    private final static Pattern loginPattern = Pattern.compile("[a-zA-Z_0-9-]+");
    private final static Pattern passwordPattern = Pattern.compile("[a-zA-Z_0-9-]+");


    @HtmlInput(type = InputType.TEXT, localizedPlaceholder = "login")
    @SqlColumn(type = SqlType.STRING, columnName = "login")
    private String login;
    @HtmlInput(type = InputType.PASSWORD, localizedPlaceholder = "password")
    @SqlColumn(type = SqlType.STRING, columnName = "password")
    private String password;

    @Override
    public boolean validate() {
        if(!login.equals("")) {
            if (!loginPattern.matcher(login).matches()) {
                addLocalizedError("errors.loginRegex");
            }
        } else {
            addLocalizedError("errors.nullLogin");
        }
        if(!password.equals("")) {
            if (!passwordPattern.matcher(password).matches()) {
                addLocalizedError("errors.passwordRegex");
            }
        } else {
            addLocalizedError("errors.nullPassword");
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
