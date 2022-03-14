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

public class AbstractDaoTest {

    private final static TestDao dao = new TestDao();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    public void simpleTest() throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()) {
            List<Sum> all = dao.abstractGetAll(connection, "select 1 + 1 as sum", Sum.class);
            assertEquals(1, all.size());
            assertEquals(all.get(0).getSum(), 2);
        }
    }

    @Test
    public void getOneByIdTest() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            DaoTestModel model = dao.abstractGetOneById(connection, "select * from dao_test where id = ?", 1L, DaoTestModel.class);
            assertEquals(1, model.getId());
            assertEquals("test", model.getName());
            assertEquals(10, model.getAge());
        }
    }

    @Test
    public void getOneByParams() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.INT)
                private final int age = 11;
                public int getAge() {return age;}
            }
            DaoTestModel model = dao.abstractGetOneByParams(connection, "select * from dao_test where age = ?", new Params(), DaoTestModel.class);
            assertEquals(2, model.getId());
            assertEquals(11, model.getAge());
        }
    }

    @Test
    public void getAllTest() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            List<DaoTestModel> models = dao.abstractGetAll(connection, "select * from dao_test", DaoTestModel.class);
            assertEquals(4, models.size());
            int i = 1;
            for (DaoTestModel model : models){
                assertEquals(i++, model.getId());
            }
        }
    }

    @Test
    public void getAllByParamsTest() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection()) {
            class Params {
                @SqlColumn(columnName = "", type = SqlType.INT)
                private final int minAge = 12;

                public int getMinAge() {
                    return minAge;
                }
            }
            List<DaoTestModel> models = dao.abstractGetAllByParams(connection, "select * from dao_test where age < ?", new Params(), DaoTestModel.class);
            assertEquals(2, models.size());
            int startAge = 10;
            for (DaoTestModel model : models) {
                assertEquals(startAge++, model.getAge());
            }
        }
    }
}
