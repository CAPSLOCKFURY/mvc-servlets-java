package utils;

import jakarta.servlet.http.HttpServletRequest;

public final class UrlUtils {
    public static String getAbsoluteUrl(String url, HttpServletRequest request){
        return request.getContextPath().concat(request.getServletPath()).concat(url);
    }

    public static String getFullUrl(HttpServletRequest request){
        String requestUrl = request.getRequestURL().toString();
        String queryParameters = request.getQueryString();
        if(queryParameters == null){
            return requestUrl;
        } else {
            return requestUrl.concat("?".concat(queryParameters));
        }
    }

    private UrlUtils(){
        throw new UnsupportedOperationException();
    }
}
