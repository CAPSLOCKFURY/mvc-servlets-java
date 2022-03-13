package models.pagination;

import models.base.pagination.Pageable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageableTest {

    @ParameterizedTest
    @MethodSource("simplePageableCases")
    public void testSimplePageable(int page, int entitiesPerPage, String sql, String expectedSql){
        Pageable pageable = new Pageable(page, entitiesPerPage);
        String paginatedSql = pageable.paginateQuery(sql);
        assertEquals(expectedSql, paginatedSql);
    }

    @ParameterizedTest
    @MethodSource("lookAheadPageableCases")
    public void testLookAheadPageable(int page, int entitiesPerPage, String sql, String expectedSql) {
        Pageable pageable = new Pageable(page, entitiesPerPage, true);
        String paginatedSql = pageable.paginateQuery(sql);
        assertEquals(expectedSql, paginatedSql);
    }

    public static Stream<Arguments> simplePageableCases(){
        return Stream.of(
                Arguments.of(1, 10, "select 1 + 1", "select 1 + 1 limit 10 offset 0"),
                Arguments.of(2, 10, "select 1 + 1", "select 1 + 1 limit 10 offset 10"),
                Arguments.of(4, 8, "select 1 + 1", "select 1 + 1 limit 8 offset 24")
        );
    }

    public static Stream<Arguments> lookAheadPageableCases(){
        return Stream.of(
                Arguments.of(1, 10, "select 1 + 1", "select 1 + 1 limit 11 offset 0"),
                Arguments.of(2, 10, "select 1 + 1", "select 1 + 1 limit 11 offset 10"),
                Arguments.of(4, 8, "select 1 + 1", "select 1 + 1 limit 9 offset 24")
        );
    }

}
