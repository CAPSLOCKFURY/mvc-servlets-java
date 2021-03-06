package forms.base.prg;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.UTF8UrlCoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CookieFormErrorsPRG implements FormErrorPRG {

    /**
     * @param errors list of error to set
     * @return Cookie which should be set in response
     */
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
        Arrays.stream(cookies).filter(c -> c.getName().equals("errors")).forEach(c -> {
            c.setValue("");
            c.setMaxAge(0);
            response.addCookie(c);
        });
    }

    @Override
    public void processErrors(HttpServletRequest request, HttpServletResponse response) {
        List<String> formErrors = getErrorsFromCookie(request.getCookies());
        request.setAttribute("errors", formErrors);
        deleteErrorCookies(request.getCookies(), response);
    }
}
