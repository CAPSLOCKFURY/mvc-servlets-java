package commands;

import commands.utils.RequestMethod;

import java.util.Objects;

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
}
