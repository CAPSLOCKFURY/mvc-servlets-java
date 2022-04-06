package models.ordering;

import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderableTest {

    @ParameterizedTest
    @MethodSource("orderableCases")
    void testOrderable(Orderable orderable, String query, String expectedSql){
        String orderedQuery = orderable.orderQuery(query);
        assertEquals(expectedSql.toLowerCase(), orderedQuery.toLowerCase());
    }

    @ParameterizedTest
    @MethodSource("orderDirectionEnumCases")
    void testOrderDirectionEnum(OrderDirection expected, String value){
        assertEquals(expected, OrderDirection.valueOfOrDefault(value));
    }

    public static Stream<Arguments> orderableCases(){
        return Stream.of(
                Arguments.of(
                        new Orderable("t", OrderDirection.ASC),
                        "select 1 + 1",
                        "select 1 + 1 order by t asc"
                ),
                Arguments.of(
                        new Orderable("abc", OrderDirection.DESC),
                        "select 2 + 2",
                        "select 2 + 2 order by abc desc"
                )
        );
    }

    public static Stream<Arguments> orderDirectionEnumCases(){
        return Stream.of(
                Arguments.of(OrderDirection.ASC, "ASC"),
                Arguments.of(OrderDirection.DESC, "DESC"),
                Arguments.of(OrderDirection.ASC, "123"),
                Arguments.of(OrderDirection.ASC, null)
        );
    }

}
