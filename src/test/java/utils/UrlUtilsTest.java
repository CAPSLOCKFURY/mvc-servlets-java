package utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlUtilsTest {

    @ParameterizedTest
    @MethodSource("getAbsoluteUrlCases")
    void testGetAbsoluteUrl(String url, String expected){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getContextPath()).thenReturn("/mockito");
        Mockito.when(request.getServletPath()).thenReturn("/app");
        String result = UrlUtils.getAbsoluteUrl(url, request);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getFullUrlCases")
    void testGetFullUrl(String url, String queryParams, String expected) {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer(url));
        Mockito.when(request.getQueryString()).thenReturn(queryParams);
        String fullUrl = UrlUtils.getFullUrl(request);
        assertEquals(expected, fullUrl);
    }

    public static Stream<Arguments> getAbsoluteUrlCases(){
        return Stream.of(
                Arguments.of("", "/mockito/app"),
                Arguments.of("/login", "/mockito/app/login"),
                Arguments.of("/admin/panel", "/mockito/app/admin/panel")
        );
    }

    public static Stream<Arguments> getFullUrlCases(){
        return Stream.of(
                Arguments.of("http://localhost:8080/project", null, "http://localhost:8080/project"),
                Arguments.of("http://localhost:8080/project", "page=1&id=3", "http://localhost:8080/project?page=1&id=3")
        );
    }

}
