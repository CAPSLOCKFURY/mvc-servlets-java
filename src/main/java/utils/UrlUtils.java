package utils;

import jakarta.servlet.http.HttpServletRequest;

public final class UrlUtils {
    public static String getAbsoluteUrl(String url, HttpServletRequest request){
        return request.getContextPath().concat(request.getServletPath()).concat(url);
    }

    private UrlUtils(){
        throw new UnsupportedOperationException();
    }
}
