package web.base.messages;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.UTF8UrlCoder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of message transport which stores messages in cookies
 * <p>
 *     Note: messages are automatically encoding into url safe format
 * </p>
 */
public class CookieMessageTransport implements MessageTransport {

    private List<String> messages = new LinkedList<>();

    private Locale locale = Locale.ROOT;

    /**
     * This method needed, if {@link #setMessage(HttpServletRequest, HttpServletResponse)} is not enough for you,
     * or you want more flexibility
     * @return Cookie which contains all messages
     */
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

    /**
     * @param cookies accepts cookies
     * @return List of decoded messages from message cookie
     */
    public static List<String> getMessagesFromCookies(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals("messages"))
                .flatMap(c -> Arrays.stream(UTF8UrlCoder.decode(c.getValue()).split(";")))
                .collect(Collectors.toList());
    }

    /**
     * Delete cookie which stores messages from request
     */
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
