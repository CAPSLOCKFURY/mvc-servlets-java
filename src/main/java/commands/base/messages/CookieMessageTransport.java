package commands.base.messages;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.UTF8UrlCoder;

import java.util.*;
import java.util.stream.Collectors;

public class CookieMessageTransport implements MessageTransport {

    private List<String> messages = new LinkedList<>();

    private Locale locale = Locale.ROOT;

    public Cookie getMessageCookie() {
        String joinedMessages = String.join(";", messages);
        String urlSafeMessages = UTF8UrlCoder.encode(joinedMessages);
        return new Cookie("messages", urlSafeMessages);
    }


    public void setMessage(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(getMessageCookie());
    }

    public List<String> getMessages(){
        return messages;
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void addLocalizedMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        messages.add(bundle.getString(key));
    }

    public static List<String> getMessagesFromCookies(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals("messages"))
                .flatMap(c -> Arrays.stream(UTF8UrlCoder.decode(c.getValue()).split(";")))
                .collect(Collectors.toList());
    }

    public void deleteMessageCookie(Cookie[] cookies, HttpServletRequest request, HttpServletResponse response){
        Arrays.stream(cookies)
                .filter(c -> c.getName().equals("messages"))
                .forEach(c -> {
                    c.setValue("");
                    c.setMaxAge(0);
                    c.setPath(request.getRequestURI());
                    response.addCookie(c);
                }
        );
    }

    public void processMessages(HttpServletRequest request, HttpServletResponse response) {
        List<String> messages = getMessagesFromCookies(request.getCookies());
        request.setAttribute("messages", messages);
        deleteMessageCookie(request.getCookies(), request, response);
    }

    public void setLocale(Locale locale){
        this.locale = locale;
    }
}
