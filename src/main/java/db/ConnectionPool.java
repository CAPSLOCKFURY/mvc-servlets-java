package db;

import exceptions.db.ConnectionCreationException;
import exceptions.db.IncorrectDriverPath;
import exceptions.db.NoAvailableConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    private static final ConnectionPool instance = new ConnectionPool();

    private String url;
    private String user;
    private String password;
    private String driverName;
    private int poolSize;

    private static BlockingQueue<Connection> connectionPool;
    private static BlockingQueue<Connection> usedConnections;

    private ConnectionPool(){
        initializeProperties();
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IncorrectDriverPath(driverName);
        }
        connectionPool = new ArrayBlockingQueue<>(poolSize);
        usedConnections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createConnection());
        }
    }

    private PooledConnection createConnection(){
        try {
            return new PooledConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new ConnectionCreationException();
        }
    }

    public static Connection getConnection() {
        if(connectionPool.size() == 0) {
            logger.error("Connection pool is empty");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        logger.debug("got connection");
        Connection connection = connectionPool.poll();
        if(connection == null){
            throw new NoAvailableConnections();
        }
        usedConnections.add(connection);
        return connection;
    }

    public static void releaseConnection(Connection connection){
        if(usedConnections.remove(connection)) {
            connectionPool.add(connection);
            logger.debug("Returned connection back to pool");
        } else {
            logger.fatal("Could not return connection to connection poll");
        }
    }

    private void initializeProperties() {
        ResourceBundle properties = ResourceBundle.getBundle("database");
        url = properties.getString("database.url");
        user = properties.getString("database.user");
        password = properties.getString("database.password");
        driverName = properties.getString("driver.name");
        poolSize = Integer.parseInt(properties.getString("pool.size"));
    }
}
