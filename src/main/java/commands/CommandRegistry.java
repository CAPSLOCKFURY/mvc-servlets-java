package commands;

import commands.base.*;
import commands.base.security.AuthenticatedOnly;
import commands.base.security.NonAuthenticatedOnly;
import commands.base.security.Security;
import exceptions.CommandNotFoundException;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import models.RoomClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RoomsService;
import utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static utils.LocaleUtils.getLocaleFromCookies;
import static utils.UrlUtils.getAbsoluteUrl;

public final class CommandRegistry {

    private static final Logger logger = LogManager.getLogger();

    private static final CommandRegistry instance = new CommandRegistry();

    private final Map<UrlBind, Command> commandMap = new HashMap<>();

    private CommandRegistry(){
        //TODO make config file for this
        List<Class<? extends Command>> commands = ClassUtils.getAnnotatedCommandClassesInPackage("commands.impl");
        registerAnnotatedCommands(commands);
        commandMap.put(new UrlBind("/register", RequestMethod.GET),
                (request, response) -> {
                    Security security = new NonAuthenticatedOnly();
                    if(!security.doSecurity(request, response)){
                        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
                    }
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("register.jsp", RequestDirection.FORWARD);
                });
        commandMap.put(new UrlBind("/login", RequestMethod.GET),
                ((request, response) -> {
                    Security security = new NonAuthenticatedOnly();
                    if(!security.doSecurity(request, response)){
                        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
                    }
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("login.jsp", RequestDirection.FORWARD);
                }));
        commandMap.put(new UrlBind("/change-language", RequestMethod.GET),
                ((request, response) -> {
                    Cookie langCookie = new Cookie("Content-Language", request.getParameter("lang"));
                    langCookie.setMaxAge(-1);
                    response.addCookie(langCookie);
                    return new CommandResult(request.getHeader("referer"), RequestDirection.REDIRECT);
                }));
        commandMap.put(new UrlBind("/profile", RequestMethod.GET),
                ((request, response) -> {
                    Security security = new AuthenticatedOnly();
                    if(!security.doSecurity(request, response)){
                        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
                    }
                    return new CommandResult("profile.jsp", RequestDirection.FORWARD);
                }));
        commandMap.put(new UrlBind("/logout", RequestMethod.GET),
                ((request, response) -> {
                    request.getSession().invalidate();
                    return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
                }));
        commandMap.put(new UrlBind("/room-request", RequestMethod.GET),
                ((request, response) -> {
                    Security security = new AuthenticatedOnly();
                    if(!security.doSecurity(request, response)){
                        return new CommandResult(getAbsoluteUrl("", request), RequestDirection.REDIRECT);
                    }
                    List<RoomClass> roomClasses = new RoomsService().getRoomClasses(getLocaleFromCookies(request.getCookies()));
                    Map<String, String> options = roomClasses.stream().collect(Collectors.toMap(c -> String.valueOf(c.getId()), RoomClass::getName));
                    request.setAttribute("roomClassesMap", options);
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("room-request.jsp", RequestDirection.FORWARD);
                }));
    }

    public Command resolveCommand(HttpServletRequest request) throws CommandNotFoundException {
        logger.debug("Resolving command");
        String url = getRequestUrl(request);
        logger.debug(url);
        UrlBind urlBind = new UrlBind(url, RequestMethod.valueOf(request.getMethod()));
        return Optional.ofNullable(commandMap.get(urlBind))
                .orElseThrow(() -> {logger.error("Command not found"); return new CommandNotFoundException();});
    }

    public static CommandRegistry getInstance() {
        return instance;
    }

    private static String getRequestUrl(HttpServletRequest request){
        String pathInfo = request.getPathInfo();
        logger.debug("Path info: " + pathInfo);
        if(pathInfo == null){
            return "";
        }
        return pathInfo.replaceAll(request.getServerName(), "");
    }

    private void registerAnnotatedCommands(List<Class<? extends Command>> commands){
        commands.forEach(c ->{
            try {
                WebMapping webMapping = c.getAnnotation(WebMapping.class);
                Command instance = c.getConstructor().newInstance();
                UrlBind bind = new UrlBind(webMapping.url(), webMapping.method());
                commandMap.put(bind, instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

}
