package forms;

import forms.base.renderers.HtmlLabelRenderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlLabelRendererTest {

    @ParameterizedTest
    @MethodSource("nonLocalizedCases")
    void testNonLocalized(String forElement, String text, String literal, String expected){
        HtmlLabelRenderer renderer = new HtmlLabelRenderer.Builder()
                .withForElement(forElement)
                .withText(text)
                .withLiteral(literal)
                .build();
        String renderedLabel = renderer.render();
        assertEquals(expected, renderedLabel);
    }


    @ParameterizedTest
    @MethodSource("localizedCases")
    void testLocalized(String forElement, String localizedText, String literal, Locale locale, String expected){
        HtmlLabelRenderer renderer = new HtmlLabelRenderer.Builder()
                .withForElement(forElement)
                .withLocalizedText(localizedText)
                .withLiteral(literal)
                .build();
        renderer.setLocale(locale);
        String renderedLabel = renderer.render();
        assertEquals(expected, renderedLabel);
    }

    public static Stream<Arguments> nonLocalizedCases(){
        return Stream.of(
                Arguments.of("test1", "Some_text", "", "<label for=\"test1\" >Some_text</label>"),
                Arguments.of("test2", "Some text", "class=\"testClass\"", "<label for=\"test2\" class=\"testClass\">Some text</label>")
        );
    }

    public static Stream<Arguments> localizedCases(){
        return Stream.of(
                Arguments.of("test1", "test", "", new Locale("en"),"<label for=\"test1\" >Test</label>"),
                Arguments.of("test2", "test", "class=\"testClass\"", new Locale("ru"), "<label for=\"test2\" class=\"testClass\">RU Test</label>")
        );
    }

}
