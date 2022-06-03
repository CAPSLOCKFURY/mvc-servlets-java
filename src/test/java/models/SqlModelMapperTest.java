package models;

import models.base.mappers.SqlModelMapper;
import models.resources.ExtendedTestModel;
import models.resources.TestModel;
import models.resources.TypePair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlModelMapperTest {

    @ParameterizedTest
    @MethodSource("sqlMapperCases")
    void testMapper(TypePair[] typePairs, Object model, Object expected) throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        for(TypePair pair : typePairs){
            if(pair.getType() == String.class){
                Mockito.when(rs.getString((String)pair.getKey())).thenReturn((String)pair.getValue());
            }
            if(pair.getType() == Integer.class){
                Mockito.when(rs.getInt((String)pair.getKey())).thenReturn((Integer) pair.getValue());
            }
            if(pair.getType() == Long.class){
                Mockito.when(rs.getLong((String)pair.getKey())).thenReturn((Long) pair.getValue());
            }
            if(pair.getType() == BigDecimal.class){
                Mockito.when(rs.getBigDecimal((String)pair.getKey())).thenReturn((BigDecimal) pair.getValue());
            }
            if(pair.getType() == java.sql.Date.class){
                Mockito.when(rs.getDate((String)pair.getKey())).thenReturn((java.sql.Date) pair.getValue());
            }
            if(pair.getType() == Boolean.class){
                Mockito.when(rs.getBoolean((String)pair.getKey())).thenReturn((Boolean) pair.getValue());
            }
        }
        SqlModelMapper modelMapper = new SqlModelMapper(model);
        modelMapper.mapFromResultSet(rs);
        assertEquals(expected, model);
    }

    public static Stream<Arguments> sqlMapperCases(){
        return Stream.of(
                Arguments.of(
                        new TypePair[]{
                                new TypePair<>("field", "fieldValue", String.class),
                                new TypePair<>("intField", 1, Integer.class),
                                new TypePair<>("stringField", "stringFieldValue", String.class)
                        },
                        new TestModel(),
                        new TestModel("fieldValue", 1, "stringFieldValue")
                ),
                Arguments.of(
                        new TypePair[]{
                                new TypePair<>("stringField", "stringField", String.class),
                                new TypePair<>("intField", 10, Integer.class),
                                new TypePair<>("longField", 10L, Long.class),
                                new TypePair<>("decimalField", new BigDecimal(111), BigDecimal.class),
                                new TypePair<>("dateField", new java.sql.Date(111), java.sql.Date.class),
                                new TypePair<>("booleanField", true, Boolean.class),
                        },
                        new ExtendedTestModel(),
                        new ExtendedTestModel("stringField", 10, 10L, new BigDecimal(111), new java.sql.Date(111), true)
                )
        );
    }
}
