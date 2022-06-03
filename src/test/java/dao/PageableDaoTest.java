package dao;

import dao.resources.TestPageableDao;
import dao.resources.models.DaoTestModel;
import db.ConnectionPool;
import models.base.SqlColumn;
import models.base.SqlType;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageableDaoTest {

    private static TestPageableDao dao;

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
        dao = new TestPageableDao(ConnectionPool.getConnection());
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void getAllTest() throws SQLException{
        Pageable pageable = new Pageable(1, 2);
        List<DaoTestModel> models = dao.abstractGetAll("select * from dao_test", DaoTestModel.class, pageable);
        assertEquals(2, models.size());
        assertEquals(1, models.get(0).getId());
        assertEquals(2, models.get(1).getId());

        Pageable secondPageable = new Pageable(2, 2);
        List<DaoTestModel> secondModels = dao.abstractGetAll("select * from dao_test", DaoTestModel.class, secondPageable);
        assertEquals(2, secondModels.size());
        assertEquals(3, secondModels.get(0).getId());
        assertEquals(4, secondModels.get(1).getId());
    }

    @Test
    void getAllByParamsTest() throws SQLException{
        class Params{
            @SqlColumn(columnName = "", type = SqlType.INT)
            private final Integer age = 12;
            public Integer getAge() {return age;}
        }
        Pageable pageable = new Pageable(1, 2);
        List<DaoTestModel> models = dao.abstractGetAllByParams("select * from dao_test where age <= ?", new Params(), DaoTestModel.class, pageable);
        assertEquals(2, models.size());
        assertEquals(1, models.get(0).getId());
        assertEquals(2, models.get(1).getId());
    }

}
