package web.base;

/**
 * Result of processing web request
 * Accepts url to which request should be forwarded or redirected, and request direction
 * if request direction is {@link RequestDirection#VOID} url does not matter, you could pass just ""
 */
public class WebResult {

    private final String url;
    private final RequestDirection direction;

    public WebResult(String url, RequestDirection direction){
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
