package models;

import models.base.SqlMapper;
import models.resources.TestModel;
import models.resources.TypePair;
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
    public void testMapper(TypePair[] typePairs, Object model, String expected) throws SQLException {
        ResultSet rs = Mockito.mock(ResultSet.class);
        for(TypePair pair : typePairs){
            if(pair.getType() == String.class){
                Mockito.when(rs.getString((String)pair.getKey())).thenReturn((String)pair.getValue());
            }
            if(pair.getType() == Integer.class){
                Mockito.when(rs.getInt((String)pair.getKey())).thenReturn((Integer) pair.getValue());
            }
        }
        SqlMapper modelMapper = new SqlMapper(model);
        modelMapper.mapFromResultSet(rs);
        assertEquals(expected, model.toString());
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
                        "TestModel{" +
                                "field='" + "fieldValue" + '\'' +
                                ", intField='" + 1 + '\'' +
                                ", field1='" + "stringFieldValue" + '\'' +
                                '}'
                )
        );
    }
}
