package tags;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormRenderHandlerTest {

    @ParameterizedTest
    @MethodSource("renderTagCases")
    public void testRenderTag(String formClass, String expected) throws IOException {
        StringJoiner renderedFormInput = new StringJoiner("\n");
        JspWriter jspWriter = Mockito.mock(JspWriter.class);
        Mockito.doAnswer(invocationOnMock -> {
                renderedFormInput.add(invocationOnMock.getArgument(0));
                return invocationOnMock;
        }).when(jspWriter).println(any(String.class));

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("Content-Language", "en")});

        PageContext pageContext = Mockito.mock(PageContext.class);
        Mockito.when(pageContext.getOut()).thenReturn(jspWriter);
        Mockito.when(pageContext.getRequest()).thenReturn(request);

        FormRenderHandler formRenderHandler = new FormRenderHandler();

        formRenderHandler.setPageContext(pageContext);
        formRenderHandler.setFormClassPath(formClass);
        formRenderHandler.doStartTag();

        assertEquals(expected, renderedFormInput.toString());
    }

    public static Stream<Arguments> renderTagCases(){
        return Stream.of(
                Arguments.of("tags.resources.TagTestForm", "<input type=\"text\" name=\"field\" >\n" +
                                                           "<input type=\"text\" name=\"field2\" placeholder=\"field2 placeholder\" >\n" +
                                                           "<input type=\"text\" name=\"field3Name\" placeholder=\"field3 placeholder\" >"),
                Arguments.of("tags.resources.LocalizedTestForm", "<input type=\"text\" name=\"login\" placeholder=\"Login\" >\n" +
                                                                 "<input type=\"password\" name=\"password\" placeholder=\"Password\" >\n" +
                                                                 "<input type=\"text\" name=\"someField\" placeholder=\"some non localized field\" >"),
                Arguments.of("tags.resources.TestSelectForm", "<input type=\"text\" name=\"testField\" >\n" +
                                                              "<select name=\"select\" >\n" +
                                                                    "\t<option value=\"Second\">2</option>\n" +
                                                                    "\t<option value=\"One\">1</option>\n" +
                                                              "</select>")
        );
    }

}
