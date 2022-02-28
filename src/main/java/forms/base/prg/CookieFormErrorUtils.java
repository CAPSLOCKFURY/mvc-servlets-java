package forms.base.prg;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import utils.UTF8UrlCoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookieFormErrorUtils {

    public static Cookie setErrorCookie(List<String> errors){
        String joinedErrors = String.join(";", errors);
        String urlSafeErrors = UTF8UrlCoder.encode(joinedErrors);
        return new Cookie("errors", urlSafeErrors);
    }

    public static List<String> getErrorsFromCookie(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals("errors"))
                .flatMap(c -> Arrays.stream(UTF8UrlCoder.decode(c.getValue()).split(";")))
                .collect(Collectors.toList());
    }

    public static void deleteErrorCookies(Cookie[] cookies, HttpServletResponse response){
        List<Cookie> deleteList = Arrays.stream(cookies).filter(c -> c.getName().equals("errors")).collect(Collectors.toList());
        deleteList.forEach(c -> {
            c.setValue(null);
            c.setMaxAge(0);
            response.addCookie(c);
        });
    }

}
