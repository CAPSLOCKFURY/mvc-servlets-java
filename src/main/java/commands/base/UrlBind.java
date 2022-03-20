package commands.base;

import java.util.Objects;

/**
 * Class for matching request to the bound {@link Command} in hash map
 */
public class UrlBind {
    private final String url;
    private final RequestMethod method;

    public UrlBind(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlBind urlBind = (UrlBind) o;
        return Objects.equals(url, urlBind.url) && method == urlBind.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }

    @Override
    public String toString() {
        return "UrlBind{" +
                "url='" + url + '\'' +
                ", method=" + method +
                '}';
    }
}
