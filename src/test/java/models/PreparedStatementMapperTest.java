package models;

import models.base.PreparedStatementMapper;
import models.resources.TestPreparedStatementForm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreparedStatementMapperTest {

    @ParameterizedTest
    @MethodSource("preparedStatementMapperCases")
    public void testPreparedStatementMapper(Object form, Object ...expectedObjects) throws SQLException {
        PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
        List<Object> setParams = new LinkedList<>();
        List<Object> expected = new ArrayList<>(Arrays.asList(expectedObjects));

        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setInt(any(Integer.class), any(Integer.class));
        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setString(any(Integer.class), any(String.class));

        PreparedStatementMapper mapper = new PreparedStatementMapper(form, stmt);
        mapper.mapToPreparedStatement();
        assertEquals(expected, setParams);
    }

    public static Stream<Arguments> preparedStatementMapperCases() {
        return Stream.of(
                Arguments.of(new TestPreparedStatementForm("field", "field2", 10), new Object[]{"field", "field2", 10}),
                Arguments.of(new TestPreparedStatementForm("stringField", "Some testing field", 123), new Object[]{"stringField", "Some testing field", 123})
        );
    }
}
