package web.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Result of {@link Command#execute(HttpServletRequest, HttpServletResponse)}
 * Accepts url to which request should be forwarded or redirected, and request direction
 * if request direction is {@link RequestDirection#VOID} url does not matter, you could pass just ""
 */
public class CommandResult {

    private final String url;
    private final RequestDirection direction;

    public CommandResult(String url, RequestDirection direction){
        this.url = url;
        this.direction = direction;
    }

    public String getUrl() {
        return url;
    }

    public RequestDirection getDirection() {
        return direction;
    }
}
