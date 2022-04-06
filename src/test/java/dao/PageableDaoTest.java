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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageableDaoTest {

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    private final static TestPageableDao dao = new TestPageableDao();

    @Test
    void getAllTest() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            Pageable pageable = new Pageable(1, 2);
            List<DaoTestModel> models = dao.abstractGetAll(connection, "select * from dao_test", DaoTestModel.class, pageable);
            assertEquals(2, models.size());
            assertEquals(1, models.get(0).getId());
            assertEquals(2, models.get(1).getId());

            Pageable secondPageable = new Pageable(2, 2);
            List<DaoTestModel> secondModels = dao.abstractGetAll(connection, "select * from dao_test", DaoTestModel.class, secondPageable);
            assertEquals(2, secondModels.size());
            assertEquals(3, secondModels.get(0).getId());
            assertEquals(4, secondModels.get(1).getId());
        }
    }

    @Test
    void getAllByParamsTest() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Params{
                @SqlColumn(columnName = "", type = SqlType.INT)
                private final Integer age = 12;
                public Integer getAge() {return age;}
            }
            Pageable pageable = new Pageable(1, 2);
            List<DaoTestModel> models = dao.abstractGetAllByParams(connection, "select * from dao_test where age <= ?", new Params(), DaoTestModel.class, pageable);
            assertEquals(2, models.size());
            assertEquals(1, models.get(0).getId());
            assertEquals(2, models.get(1).getId());
        }
    }

}
