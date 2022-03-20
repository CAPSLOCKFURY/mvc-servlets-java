package commands.base.messages;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Locale;

public interface MessageTransport {
    void setMessage(HttpServletRequest request, HttpServletResponse response);
    void processMessages(HttpServletRequest request, HttpServletResponse response);
    void addMessage(String message);
    void addLocalizedMessage(String key);
    List<String> getMessages();
    void setLocale(Locale locale);
}
