package commands;

import commands.impl.LoginPostCommand;
import commands.impl.MainCommand;
import commands.impl.RegisterPostCommand;
import commands.utils.RequestDirection;
import commands.utils.RequestMethod;
import exceptions.CommandNotFoundException;
import forms.base.prg.FormErrorPRG;
import forms.base.prg.CookieFormErrorsPRG;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CommandRegistry {

    private static final CommandRegistry instance = new CommandRegistry();

    private final Map<UrlBind, Command> commandMap = new HashMap<>();

    private CommandRegistry(){
        commandMap.put(new UrlBind("", RequestMethod.GET), new MainCommand());
        commandMap.put(new UrlBind("/register", RequestMethod.GET),
                (request, response) -> {
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("register.jsp", RequestDirection.FORWARD);
                });
        commandMap.put(new UrlBind("/register", RequestMethod.POST), new RegisterPostCommand());
        commandMap.put(new UrlBind("/login", RequestMethod.GET),
                ((request, response) -> {
                    FormErrorPRG errorProcessor = new CookieFormErrorsPRG();
                    errorProcessor.processErrors(request, response);
                    return new CommandResult("login.jsp", RequestDirection.FORWARD);
                }));
        commandMap.put(new UrlBind("/login", RequestMethod.POST), new LoginPostCommand());
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
        return pathInfo.replaceAll("s".replace("s", request.getServerName()), "");
    }

}
