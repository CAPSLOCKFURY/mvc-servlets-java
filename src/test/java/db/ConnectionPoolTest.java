package db;

import exceptions.db.NoAvailableConnections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionPoolTest {

    private static int POOL_SIZE;

    @BeforeAll
    public static void setUp(){
        ResourceBundle properties = ResourceBundle.getBundle("database");
        POOL_SIZE = Integer.parseInt(properties.getString("pool.size"));
        ConnectionPool.initPool();
    }

    @AfterEach
    public void tearDownAfterTest(){
        ConnectionPool.releaseAllConnections();
    }

    @Test
    void testConnectionPoolTrowsWhenEmpty(){
        for (int i = 0; i < POOL_SIZE; i++) {
            ConnectionPool.getConnection();
        }
        assertThrows(NoAvailableConnections.class, ConnectionPool::getConnection);
    }

    @Test
    void testConnectionPoolReleaseConnection(){
        int cpSizeBefore = ConnectionPool.getCurrentPoolSize();
        Connection connection = ConnectionPool.getConnection();
        int cpSizeAfter = ConnectionPool.getCurrentPoolSize();
        assertEquals(cpSizeBefore - 1, cpSizeAfter);
        ConnectionPool.releaseConnection(connection);
        assertEquals(cpSizeBefore, ConnectionPool.getCurrentPoolSize());
    }

    @Test
    void testPooledConnectionNotClosing() throws SQLException {
        int cpSizeBefore = ConnectionPool.getCurrentPoolSize();
        Connection connection = ConnectionPool.getConnection();
        connection.close();
        assertFalse(connection.isClosed());
        assertEquals(cpSizeBefore, ConnectionPool.getCurrentPoolSize());
    }

    @Test
    void testPooledConnectionPreparedStatementCache() throws SQLException {
        Connection connection = ConnectionPool.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select 1 + 1");
        PreparedStatement stmt1 = connection.prepareStatement("select 1 + 1");
        assert stmt == stmt1;
        Connection connection1 = ConnectionPool.getConnection();
        PreparedStatement stmt2 = connection1.prepareStatement("select 1 + 1");
        assert stmt2 != stmt;
    }
}
