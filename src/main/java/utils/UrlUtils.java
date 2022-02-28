package utils;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtils {
    public static String getAbsoluteUrl(String url, HttpServletRequest request){
        return request.getContextPath().concat(request.getServletPath()).concat(url);
    }
}
