package commands.impl;

import commands.base.*;
import dao.dao.UserDao;
import dao.factory.DaoAbstractFactory;
import dao.factory.SqlDB;
import forms.RegisterForm;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import utils.LocaleUtils;

import java.util.Locale;

import static utils.UrlUtils.getAbsoluteUrl;

@WebMapping(url = "/register", method = RequestMethod.POST)
public class RegisterPostCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        RegisterForm form = new RegisterForm();
        form.mapRequestToForm(request);
        form.setLocale(new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if(isValid) {
            UserDao dao = DaoAbstractFactory.getFactory(SqlDB.POSTGRESQL).getUserDao();
            boolean result = dao.createUser(form);
        }
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        if(isValid){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        } else {
            return new CommandResult(getAbsoluteUrl("/register", request), RequestDirection.REDIRECT);
        }
    }
}
