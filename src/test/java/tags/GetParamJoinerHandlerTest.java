package tags;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class GetParamJoinerHandlerTest {

    @ParameterizedTest
    @MethodSource("getParamJoinerCases")
    public void testGetParamJoiner(Map<String, String[]> paramMap, String ignoreParams, boolean insertQuestionMark, String expected) throws IOException {
        AtomicReference<String> result = new AtomicReference<>();
        JspWriter writer = Mockito.mock(JspWriter.class);
        Mockito.doAnswer(invocationOnMock -> {
            result.set(invocationOnMock.getArgument(0));
            return invocationOnMock;
        }).when(writer).println(any(String.class));
        PageContext pageContext = Mockito.mock(PageContext.class);
        Mockito.when(pageContext.getOut()).thenReturn(writer);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameterMap()).thenReturn(paramMap);

        Mockito.when(pageContext.getRequest()).thenReturn(request);

        GetParamJoinerHandler tag = new GetParamJoinerHandler();

        tag.setPageContext(pageContext);
        tag.setIgnoreParams(ignoreParams);
        tag.setInsertQuestionMark(insertQuestionMark);
        tag.doStartTag();
        assertEquals(expected, result.get());
    }

    public static Stream<Arguments> getParamJoinerCases(){
        return Stream.of(
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                    }
                }, null, true, "?page=1&id=3"),
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                    }
                }, null, false, "&page=1&id=3"),
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                    }
                }, "page", true, "?id=3"),
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                        put("values", new String[]{"1", "2", "3"});
                    }
                }, null, true, "?values=1&values=2&values=3&page=1&id=3"),
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                        put("values", new String[]{"1", "2", "3"});
                    }
                }, "values", true, "?page=1&id=3"),
                Arguments.of(new HashMap<String, String[]>(){
                    {
                        put("page", new String[]{"1"});
                        put("id", new String[]{"3"});
                        put("values", new String[]{"1", "2", "3"});
                    }
                }, "values,id", true, "?page=1")
        );
    }

}
