package web.base.messages;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Locale;

/**
 * Interface for defining strategy of transporting messages between page redirect
 */
public interface MessageTransport {
    /**
     * Sets all added messages
     */
    void setMessage(HttpServletRequest request, HttpServletResponse response);

    /**
     * Processes messages on the receiving side, for e.g. setting messages into jsp attribute
     */
    void processMessages(HttpServletRequest request, HttpServletResponse response);

    void addMessage(String message);

    /**
     * Sets the localized message
     * <p>
     *     Note: You should create Resourse Bundle named messages
     * </p>
     * @param key key in resource bundle of message
     */
    void addLocalizedMessage(String key);

    List<String> getMessages();

    void setLocale(Locale locale);
}
