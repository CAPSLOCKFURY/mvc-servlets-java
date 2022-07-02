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

    private final List<String> messages = new LinkedList<>();

    private Locale locale = Locale.ROOT;

    private String namespace = "messages";

    private String bundleName = "messages";

    private Cookie getMessageCookie() {
        String joinedMessages = String.join(";", messages);
        String urlSafeMessages = UTF8UrlCoder.encode(joinedMessages);
        return new Cookie(namespace, urlSafeMessages);
    }

    @Override
    public void setMessage(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(getMessageCookie());
    }

    @Override
    public List<String> getMessages(){
        return messages;
    }

    @Override
    public void addMessage(String message){
        messages.add(message);
    }

    @Override
    public void addLocalizedMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        messages.add(bundle.getString(key));
    }

    @Override
    public void processMessages(HttpServletRequest request, HttpServletResponse response) {
        List<String> messages = getMessagesFromCookies(request.getCookies());
        request.setAttribute(namespace, messages);
        deleteMessageCookie(request.getCookies(), request, response);
    }

    private List<String> getMessagesFromCookies(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(namespace))
                .flatMap(c -> Arrays.stream(UTF8UrlCoder.decode(c.getValue()).split(";")))
                .collect(Collectors.toList());
    }

    private void deleteMessageCookie(Cookie[] cookies, HttpServletRequest request, HttpServletResponse response){
        Arrays.stream(cookies)
                .filter(c -> c.getName().equals(namespace))
                .forEach(c -> {
                            c.setValue("");
                            c.setMaxAge(0);
                            c.setPath(request.getRequestURI());
                            response.addCookie(c);
                        }
                );
    }

    @Override
    public void setLocale(Locale locale){
        this.locale = locale;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }
}
