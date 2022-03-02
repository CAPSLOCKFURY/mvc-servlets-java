package utils;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocaleUtilsTest {

    @ParameterizedTest
    @MethodSource("getLocaleFromCookiesCases")
    public void testGetLocaleFromCookies(Cookie[] cookies, String expectedLocale){
        String contentLang = LocaleUtils.getLocaleFromCookies(cookies);
        assertEquals(contentLang, expectedLocale);
    }

    public static Stream<Arguments> getLocaleFromCookiesCases(){
        return Stream.of(
                Arguments.of(new Cookie[]{}, "en"),
                Arguments.of(null, "en"),
                Arguments.of(
                        new Cookie[]{
                                new Cookie("test", "test"),
                                new Cookie("SomeCookie", "SomeValue"),
                                new Cookie("Content-Language", "ru")
                        },
                        "ru"
                ),
                Arguments.of(
                        new Cookie[]{
                                new Cookie("test", "test"),
                                new Cookie("SomeCookie", "SomeValue"),
                        },
                        "en"
                )
        );
    }

}
