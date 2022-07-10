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
import web.base.RequestDirection;
import web.base.RequestMethod;
import web.base.WebResult;
import web.base.annotations.WebController;
import web.base.annotations.WebMapping;
import web.base.security.annotations.AuthenticatedOnly;
import web.base.security.annotations.NonAuthenticatedOnly;
import web.resolvers.annotations.Form;


@WebController
public class AuthController {

    private final UserService userService = UserService.getInstance();

    @NonAuthenticatedOnly("")
    @WebMapping(url = "/login", method = RequestMethod.GET)
    public WebResult showLogin(HttpServletRequest request, HttpServletResponse response){
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new WebResult("login.jsp", RequestDirection.FORWARD);
    }

    @NonAuthenticatedOnly("")
    @WebMapping(url = "/login", method = RequestMethod.POST)
    public WebResult login(HttpServletResponse response, @Form LoginForm form, HttpSession session) {
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/login", RequestDirection.REDIRECT);
        }
        User user = userService.loginUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/login", RequestDirection.REDIRECT);
        }
        session.setAttribute("user", user);
        return new WebResult("/profile", RequestDirection.REDIRECT);
    }

    @NonAuthenticatedOnly("")
    @WebMapping(url = "/register", method = RequestMethod.GET)
    public WebResult showRegister(HttpServletRequest request, HttpServletResponse response){
        FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
        errorProcessor.processErrors(request, response);
        return new WebResult("register.jsp", RequestDirection.FORWARD);
    }

    @WebMapping(url = "/register", method = RequestMethod.POST)
    public WebResult register(HttpServletResponse response, @Form RegisterForm form, HttpSession session) {
        boolean isValid = form.validate();
        if (!isValid) {
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/register", RequestDirection.REDIRECT);
        }
        long id = userService.createUser(form);
        if(!form.isValid()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/register", RequestDirection.REDIRECT);
        }
        User user = new User(form);
        user.setId(id);
        session.setAttribute("user", user);
        return new WebResult("/profile", RequestDirection.REDIRECT);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/change-password", method = RequestMethod.GET)
    public WebResult showChangePassword(HttpServletRequest request, HttpServletResponse response) {
        FormErrorPRG formErrorPRG = new CookieFormErrorsPRG();
        formErrorPRG.processErrors(request, response);
        return new WebResult("change-password.jsp", RequestDirection.FORWARD);
    }

    @AuthenticatedOnly("")
    @WebMapping(url = "/profile/change-password", method = RequestMethod.POST)
    public WebResult changePassword(HttpServletResponse response,
                                    @Form ChangePasswordForm form, User user, HttpSession session) {
        if(!form.validate()){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
        }
        Long userId = user.getId();
        boolean isChanged = userService.changeUserPassword(form, userId);
        if(!isChanged){
            response.addCookie(CookieFormErrorsPRG.setErrorCookie(form.getErrors()));
            return new WebResult("/profile/change-password", RequestDirection.REDIRECT);
        }
        session.invalidate();
        return new WebResult("/login", RequestDirection.REDIRECT);
    }

    @WebMapping(url = "/logout", method = RequestMethod.GET)
    public WebResult logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new WebResult("", RequestDirection.REDIRECT);
    }

}
