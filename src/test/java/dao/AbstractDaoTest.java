package dao;

import dao.resources.TestDao;
import dao.resources.models.DaoTestModel;
import dao.resources.models.Sum;
import db.ConnectionPool;
import models.base.SqlColumn;
import models.base.SqlType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractDaoTest {

    private static TestDao dao;

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
        dao = new TestDao(ConnectionPool.getConnection());
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void simpleTest() throws SQLException {
        List<Sum> all = dao.abstractGetAll("select 1 + 1 as sum", Sum.class);
        assertEquals(1, all.size());
        assertEquals(2, all.get(0).getSum());
    }

    @Test
    void getOneByIdTest() throws SQLException{
        DaoTestModel model = dao.abstractGetOneById("select * from dao_test where id = ?", 1L, DaoTestModel.class);
        assertEquals(1, model.getId());
        assertEquals("test", model.getName());
        assertEquals(10, model.getAge());
    }

    @Test
    void getOneByParams() throws SQLException{
        class Params{
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final int age = 11;
            public int getAge() {return age;}
        }
        DaoTestModel model = dao.abstractGetOneByParams("select * from dao_test where age = ?", new Params(), DaoTestModel.class);
        assertEquals(2, model.getId());
        assertEquals(11, model.getAge());
    }

    @Test
    void getAllTest() throws SQLException{
        List<DaoTestModel> models = dao.abstractGetAll("select * from dao_test", DaoTestModel.class);
        assertEquals(4, models.size());
        int i = 1;
        for (DaoTestModel model : models){
            assertEquals(i++, model.getId());
        }
    }

    @Test
    void getAllByParamsTest() throws SQLException {
        class Params {
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final int minAge = 12;

            public int getMinAge() {
                return minAge;
            }
        }
        List<DaoTestModel> models = dao.abstractGetAllByParams("select * from dao_test where age < ?", new Params(), DaoTestModel.class);
        assertEquals(2, models.size());
        int startAge = 10;
        for (DaoTestModel model : models) {
            assertEquals(startAge++, model.getAge());
        }
    }

    @Test
    void createEntityTest() throws SQLException{
        class InsertParams{
            @SqlColumn(columnName = "", type = SqlType.STRING)
            private final String name = "testIns";
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final Integer age = 15;
            public String getName() {return name;}
            public Integer getAge() {return age;}
        }
        boolean result = dao.abstractCreateEntity("insert into dao_test_insert(name, age) values (?, ?)", new InsertParams());
        assertTrue(result);
    }

    @Test
    void createEntityAndGetIdTest() throws SQLException{
        class InsertParams{
            @SqlColumn(columnName = "", type = SqlType.STRING)
            private final String name = "testInsId";
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final Integer age = 16;
            public String getName() {return name;}
            public Integer getAge() {return age;}
        }
        long id = dao.abstractCreateEntityAndGetId("insert into dao_test_insert(name, age) values (?, ?)", new InsertParams());
        assertTrue(id > 0);
    }

    @Test
    void updateEntityByIdTest() throws SQLException{
        class UpdateParams{
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final Integer age = 20;
            public Integer getAge() {return age;}
        }
        boolean result = dao.abstractUpdateEntityById("update dao_test_insert set age = ? where id = ?", new UpdateParams(), 1L);
        assertTrue(result);
    }

    @Test
    void updateEntityTest() throws SQLException{
        class UpdateParams{
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final Integer age = 21;
            @SqlColumn(columnName = "", type = SqlType.LONG)
            private final Long id = 1L;
            public Integer getAge() {return age;}
            public Long getId() {return id;}
        }
        boolean result = dao.abstractUpdateEntity("update dao_test_insert set age = ? where id = ?", new UpdateParams());
        assertTrue(result);
    }
}
