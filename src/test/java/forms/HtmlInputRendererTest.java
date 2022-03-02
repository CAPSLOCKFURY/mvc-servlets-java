package forms;

import forms.base.HtmlInputRenderer;
import forms.base.InputType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlInputRendererTest {

    @ParameterizedTest
    @MethodSource("nonLocalizedCases")
    public void testNonLocalized(InputType type, String name, String placeholder, String expected){
        HtmlInputRenderer renderer = new HtmlInputRenderer(type, name, placeholder, null);
        String renderedInput = renderer.construct();
        assertEquals(expected, renderedInput);
    }

    @ParameterizedTest
    @MethodSource("localizedCases")
    public void testLocalized(InputType type, String name, String localizedPlaceholder, String locale, String expected){
        HtmlInputRenderer renderer = new HtmlInputRenderer(type, name, "", localizedPlaceholder);
        renderer.setLocale(locale);
        String renderedInput = renderer.construct();
        assertEquals(expected, renderedInput);
    }

    public static Stream<Arguments> nonLocalizedCases(){
        return Stream.of(
                Arguments.of(InputType.TEXT, "test", "test", "<input type=\"text\" name=\"test\" placeholder=\"test\" >"),
                Arguments.of(InputType.PASSWORD, "test1", "Test Testing", "<input type=\"password\" name=\"test1\" placeholder=\"Test Testing\" >"),
                Arguments.of(InputType.TEXT, "test", "", "<input type=\"text\" name=\"test\" >")
        );
    }

    public static Stream<Arguments> localizedCases(){
        return Stream.of(
                Arguments.of(InputType.TEXT, "test", "test1", "ru", "<input type=\"text\" name=\"test\" placeholder=\"RuTest1\" >"),
                Arguments.of(InputType.PASSWORD, "test123", "test2", "en", "<input type=\"password\" name=\"test123\" placeholder=\"Test2\" >"),
                Arguments.of(InputType.TEXT, "test", "test3", "en", "<input type=\"text\" name=\"test\" placeholder=\"Test3\" >"),
                Arguments.of(InputType.TEXT, "test", "test4", "ru", "<input type=\"text\" name=\"test\" placeholder=\"RuTest4\" >")
        );
    }

}
