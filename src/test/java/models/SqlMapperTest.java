package models;

import forms.resourses.StringIntPair;
import forms.resourses.StringPair;
import models.base.SqlMapper;
import models.resources.TestModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlMapperTest {

    @ParameterizedTest
    @MethodSource("sqlMapperCases")
    public void testMapper(StringPair[] pairs, StringIntPair[] stringIntPairs, Object model, String expected) throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        for(StringPair pair : pairs){
            Mockito.when(rs.getString(pair.getKey())).thenReturn(pair.getValue());
        }
        for (StringIntPair pair : stringIntPairs){
            Mockito.when(rs.getInt(pair.getKey())).thenReturn(pair.getValue());
        }
        SqlMapper modelMapper = new SqlMapper(model);
        modelMapper.mapFromResultSet(rs);
        assertEquals(expected, model.toString());
    }

    public static Stream<Arguments> sqlMapperCases(){
        return Stream.of(
                Arguments.of(
                        new StringPair[]{new StringPair("field", "fieldValue"), new StringPair("stringField", "stringFieldValue")},
                        new StringIntPair[]{new StringIntPair("intField", 1)},
                        new TestModel(),
                        "TestModel{" +
                                "field='" + "fieldValue" + '\'' +
                                ", intField='" + 1 + '\'' +
                                ", field1='" + "stringFieldValue" + '\'' +
                                '}'
                )
        );
    }
}
