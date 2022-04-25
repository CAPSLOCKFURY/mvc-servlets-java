package controllers;

import forms.ChangePasswordForm;
import forms.LoginForm;
import forms.RegisterForm;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import service.UserService;
import utils.LocaleUtils;
import web.base.*;
import web.base.security.AuthenticatedOnly;
import web.base.security.NonAuthenticatedOnly;
import web.base.security.Security;

import java.util.Locale;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

@WebController
public class AuthController {

    private final UserService userService = UserService.getInstance();

    @WebMapping(url = "/login", method = RequestMethod.GET)
    public CommandResult showLogin(HttpServletRequest request, HttpServletResponse response){
        Security security = new NonAuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new CommandResult("login.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/login", method = RequestMethod.POST)
    public CommandResult login(HttpServletRequest request, HttpServletResponse response) {
        Security security = new NonAuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        LoginForm form = new LoginForm();
        form.mapRequestToForm(request);
        form.setLocale(new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
        }
        User user = userService.loginUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/register", method = RequestMethod.GET)
    public CommandResult showRegister(HttpServletRequest request, HttpServletResponse response){
        Security security = new NonAuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new CommandResult("register.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/register", method = RequestMethod.POST)
    public CommandResult register(HttpServletRequest request, HttpServletResponse response) {
        RegisterForm form = new RegisterForm();
        form.mapRequestToForm(request);
        form.setLocale(new Locale(LocaleUtils.getLocaleFromCookies(request.getCookies())));
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/register", request), RequestDirection.REDIRECT);
        }
        long id = userService.createUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/register", request), RequestDirection.REDIRECT);
        }
        HttpSession session = request.getSession();
        User user = new User(form);
        user.setId(id);
        session.setAttribute("user", user);
        return new CommandResult(getAbsoluteUrl("/profile", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/profile/change-password", method = RequestMethod.GET)
    public CommandResult showChangePassword(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        FormErrorPRG formErrorPRG = new CookieFormErrorsPRG();
        formErrorPRG.processErrors(request, response);
        return new CommandResult("change-password.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/profile/change-password", method = RequestMethod.POST)
    public CommandResult changePassword(HttpServletRequest request, HttpServletResponse response) {
        Security security = new AuthenticatedOnly();
        if(!security.doSecurity(request, response)){
            return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
        }
        ChangePasswordForm form = new ChangePasswordForm();
        form.setLocale(new Locale(getLocaleFromCookies(request.getCookies())));
        form.mapRequestToForm(request);
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        HttpSession session = request.getSession();
        Long userId = ((User) session.getAttribute("user")).getId();
        boolean isChanged = userService.changeUserPassword(form, userId);
        if(!isChanged){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new CommandResult(getAbsoluteUrl("/profile/change-password", request), RequestDirection.REDIRECT);
        }
        session.invalidate();
        return new CommandResult(getAbsoluteUrl("/login", request), RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/logout", method = RequestMethod.GET)
    public CommandResult logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
    }

}
