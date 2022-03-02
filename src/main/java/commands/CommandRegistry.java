package commands;

import commands.base.*;
import exceptions.CommandNotFoundException;
import forms.base.prg.CookieFormErrorsPRG;
import forms.base.prg.FormErrorPRG;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CommandRegistry {

    private static final CommandRegistry instance = new CommandRegistry();

    private final Map<UrlBind, Command> commandMap = new HashMap<>();

    private CommandRegistry(){
        //TODO make config file for this
        List<Class<? extends Command>> commands = ClassUtils.getAnnotatedCommandClassesInPackage("commands.impl");
        registerAnnotatedCommands(commands);
        commandMap.put(new UrlBind("/register", RequestMethod.GET),
                (request, response) -> {
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("register.jsp", RequestDirection.FORWARD);
                });
        commandMap.put(new UrlBind("/login", RequestMethod.GET),
                ((request, response) -> {
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
    }

    public Command resolveCommand(HttpServletRequest request) throws CommandNotFoundException {
        System.out.println("Resolving command");
        String url = getRequestUrl(request);
        System.out.println(url);
        UrlBind urlBind = new UrlBind(url, RequestMethod.valueOf(request.getMethod()));
        return Optional.ofNullable(commandMap.get(urlBind))
                .orElseThrow(CommandNotFoundException::new);
    }

    public static CommandRegistry getInstance() {
        return instance;
    }

    private static String getRequestUrl(HttpServletRequest request){
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            return "";
        }
        System.out.println("Path info: " + pathInfo);
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
