package dao;

import dao.resources.TestOrderableDao;
import dao.resources.models.DaoTestModel;
import db.ConnectionPool;
import models.base.SqlColumn;
import models.base.SqlType;
import models.base.ordering.OrderDirection;
import models.base.ordering.Orderable;
import models.base.pagination.Pageable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderableDaoTest {

    private final static TestOrderableDao dao = new TestOrderableDao();

    @BeforeAll
    public static void setUp(){
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void getAllTest() throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            Pageable pageable = new Pageable(1, 4);
            Orderable orderable = new Orderable("id", OrderDirection.DESC);
            List<DaoTestModel> models = dao.abstractGetAll(connection, "select * from dao_test", DaoTestModel.class, orderable, pageable);
            assertEquals(4, models.size());
            int id = 4;
            for (DaoTestModel model : models){
                assertEquals(id--, model.getId());
            }

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
            Pageable pageable = new Pageable(1, 4);
            Orderable orderable = new Orderable("id", OrderDirection.DESC);
            List<DaoTestModel> models = dao.abstractGetAllByParams(connection, "select * from dao_test where age <= ?", new Params(), DaoTestModel.class, orderable, pageable);
            assertEquals(3, models.size());
            int id = 3;
            for (DaoTestModel model : models){
                assertEquals(id--, model.getId());
            }
        }
    }

}