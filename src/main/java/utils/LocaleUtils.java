package utils;

import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public final class LocaleUtils {

    private final static HashMap<String, Locale> localeCache = new HashMap();

    @Deprecated
    public static Locale of(String locale){
        Locale cachedLocale = localeCache.get(locale);
        if(cachedLocale == null){
            Locale newLocale = new Locale(locale);
            localeCache.put(locale, new Locale(locale));
            return newLocale;
        }
        return cachedLocale;
    }

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
