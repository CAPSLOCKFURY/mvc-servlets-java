package forms;

import forms.base.renderers.HtmlTextAreaRenderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlTextAreaRendererTest {

    @ParameterizedTest
    @MethodSource("testCases")
    public void test(String rows, String cols, String name, String id, String literal, String expected){
        HtmlTextAreaRenderer renderer = new HtmlTextAreaRenderer.Builder()
                .withRows(rows).withCols(cols)
                .withName(name)
                .withId(id)
                .withLiteral(literal)
                .build();
        String renderedTextArea = renderer.render();
        assertEquals(expected, renderedTextArea);
    }

    public static Stream<Arguments> testCases(){
        return Stream.of(
            Arguments.of("10", "10", "test", "testId", "", "<textarea name=\"test\" rows=\"10\" cols=\"10\" id=\"testId\" >\n</textarea>"),
            Arguments.of("100", "110", "test", "testId", "class=\"testClass\"", "<textarea name=\"test\" rows=\"100\" cols=\"110\" id=\"testId\" class=\"testClass\">\n</textarea>")
        );
    }
}
