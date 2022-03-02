package forms;

import forms.resourses.StringPair;
import forms.resourses.TestForm;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormRequestMapperTest {

    @ParameterizedTest
    @MethodSource("mapperCases")
    public final void testMapper(StringPair[] pairs, String expected){
        TestForm form = new TestForm();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Arrays.stream(pairs).forEach(pair ->
                Mockito.doReturn(pair.getValue()).when(request).getParameter(pair.getKey()));
        form.mapRequestToForm(request);
        assertEquals(expected, form.toString());
    }

    public static Stream<Arguments> mapperCases(){
        return Stream.of(
            Arguments.of(new StringPair[]{new StringPair("testField", "test123"), new StringPair("testName", "testNameTest"), new StringPair("stringField", "fieeeeld")},
                    "TestForm{testField='test123', testNameField='testNameTest', stringField='fieeeeld'}"
            ),
            Arguments.of(new StringPair[]{new StringPair("testField", "testField"), new StringPair("testName", "testNameTest12345"), new StringPair("stringField", "fieeeeld"), new StringPair("nonExistingField", "value")},
                    "TestForm{testField='testField', testNameField='testNameTest12345', stringField='fieeeeld'}"
            ),
            Arguments.of(new StringPair[]{new StringPair("testField", "4"), new StringPair("testName", "testNameTest123")},
                    "TestForm{testField='4', testNameField='testNameTest123', stringField='null'}"
            )
        );
    }
}