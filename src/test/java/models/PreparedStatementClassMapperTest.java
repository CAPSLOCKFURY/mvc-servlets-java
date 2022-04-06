package models;

import models.base.mappers.PreparedStatementClassMapper;
import models.resources.TestExtendedForm;
import models.resources.TestPreparedStatementForm;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PreparedStatementClassMapperTest {

    @ParameterizedTest
    @MethodSource("preparedStatementMapperCases")
    void testPreparedStatementMapper(Object form, Object ...expectedObjects) throws SQLException {
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

        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setLong(any(Integer.class), any(Long.class));

        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setBigDecimal(any(Integer.class), any(BigDecimal.class));

        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setDate(any(Integer.class), any(java.sql.Date.class));

        Mockito.doAnswer(invocationOnMock -> {
            setParams.add(invocationOnMock.getArgument(1));
            return invocationOnMock;
        }).when(stmt).setBoolean(any(Integer.class), any(Boolean.class));

        PreparedStatementClassMapper mapper = new PreparedStatementClassMapper(form);
        mapper.mapToPreparedStatement(stmt);
        assertEquals(expected, setParams);
    }

    public static Stream<Arguments> preparedStatementMapperCases() {
        return Stream.of(
                Arguments.of(new TestPreparedStatementForm("field", "field2", 10), new Object[]{"field", "field2", 10}),
                Arguments.of(new TestPreparedStatementForm("stringField", "Some testing field", 123), new Object[]{"stringField", "Some testing field", 123}),
                Arguments.of(new TestExtendedForm("stringField", 10, 100L, new BigDecimal(123), new java.sql.Date(111), true), new Object[]{"stringField", 10, 100L, new BigDecimal(123), new java.sql.Date(111), true})
        );
    }
}
