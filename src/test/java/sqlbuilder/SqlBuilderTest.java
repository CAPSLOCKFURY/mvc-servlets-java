package sqlbuilder;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sqlbuilder.builder.base.SortDirection;
import sqlbuilder.builder.SqlBuilder;
import sqlbuilder.model.SqlModel;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlBuilderTest {

    @ParameterizedTest
    @MethodSource("sqlBuilderCases")
    void
    testSqlBuilder(String actual, String expected){
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> sqlBuilderCases(){
        SqlModel m = new SqlModel();
        SqlBuilder sb = new SqlBuilder();
        return Stream.of(
                Arguments.of(sb.select(m.get("a"), m.get("b")).from("table").clear(), "select a, b from table"),
                Arguments.of(sb
                        .select(m.get("a"), m.get("b"))
                        .from(sb.subquery().select(m.get("a"), m.get("b")).from("table2"))
                        .orderBy(m.get("a"), SortDirection.DESC)
                        .clear(),
                        "select a, b from (select a, b from table2) order by a desc"
                ),
                Arguments.of(sb
                        .select(m.get("abc")).from(m.get("table3"))
                        .join(m.get("table4"), "").on(m.get("abc").eq(m.get("table4.abc")))
                        .clear(),
                        "select abc from table3 inner join table4 on abc = table4.abc"
                ),
                Arguments.of(sb
                        .select(m.get("a")).from("sample_table").where(m.get("a").eq(1))
                        .and(m.get("b").eq("'abc'"))
                                .or(
                                        m.get("c").eq(1L)
                                        .and(m.get("c").eq(2L))
                        ).clear(),
                        "select a from sample_table where a = 1 and b = 'abc' or (c = 1 and c = 2)"
                ),
                Arguments.of(sb
                        .select(m.get("a")).from("table").where(m.get("b").eq(sb.subquery().select(m.get("a").sum()).from("table2")))
                        .clear(),
                        "select a from table where b = (select sum(a) from table2)"
                ),
                Arguments.of(sb
                        .select(m.get("a"), m.get("b"))
                        .from("table")
                        .where(m.get("a").eq(1)
                                .or(
                                        m.get("b").eq(2).or(m.get("c").eq(3)).and(m.get("d").eq(4))))
                                        .or(m.get("e").eq(5).and(m.get("f").eq(6)))
                        .clear(),
                        "select a, b from table where (a = 1 or (b = 2 or c = 3 and d = 4)) or (e = 5 and f = 6)"
                ),
                Arguments.of(sb.select(m.get("a").sum().as("sm")).from("table").clear(), "select sum(a) as sm from table"),
                Arguments.of(sb.select(m.get("a").as("b")).from("table").where(m.get("b").sum().eq(100L)).clear(),
                        "select a as b from table where sum(b) = 100"),
                Arguments.of(sb.
                        select(m.get("a").not()).from("table").where(m.get("b").not().eq(1)).clear(),
                        "select not a from table where not b = 1"
                ),
                Arguments.of(sb
                        .select(m.get("a")).from("table").where(m.get("a").eq(1))
                                .or(m.get("b").not().eq(1).or(m.get("c").eq(1)))
                        .clear(),
                        "select a from table where a = 1 or (not b = 1 or c = 1)"
                )
        );
    }

}
