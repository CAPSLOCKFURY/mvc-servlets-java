package utils;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class LocaleUtils {

    /**
     * @param cookies cookies, in which locale will be searched
     * @return String of found locale or default value ("en")
     */
    public static String getLocaleFromCookies(Cookie[] cookies){
        if(cookies == null || cookies.length == 0){
            return "en";
        }
        Map<String, String> cookieMap = Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
        return cookieMap.getOrDefault("Content-Language", "en");
    }

    private LocaleUtils(){
        throw new UnsupportedOperationException();
    }
}
