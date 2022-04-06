package db;

import exceptions.db.ConnectionCreationException;
import exceptions.db.ConnectionNotReturned;
import exceptions.db.IncorrectDriverPath;
import exceptions.db.NoAvailableConnections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool class, you should create database.properties file, from which properties will be loaded
 * <p>
 * Example of Database properties file
 *  database.url = jdbc:postgresql://localhost:5432/table
 *  database.user = admin
 *  database.password = admin
 *  driver.name = org.postgresql.Driver
 *  pool.size = 10
 *  pool.freeConnectionWaitTime = 3000
 * </p>
 * <p>
 *     Note: Connections which are returned are {@link PooledConnection} class
 * </p>
 */
public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER_NAME;
    private static int POOL_SIZE;
    private static int FREE_CONNECTION_WAIT_TIME;

    private static BlockingQueue<Connection> pool;
    private static BlockingQueue<Connection> usedConnections;

    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean POOL_INITIALIZED = false;

    private ConnectionPool(){
        initializeProperties();
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IncorrectDriverPath(DRIVER_NAME);
        }
        pool = new ArrayBlockingQueue<>(POOL_SIZE);
        usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        POOL_INITIALIZED = true;
        logger.info("Connection pool initialized");
    }

    /**
     * Initializes connection pool, if it is not initialized
     */
    public static void initPool(){
        lock.lock();
        try{
            if(!POOL_INITIALIZED){
                new ConnectionPool();
            }
        } finally {
            lock.unlock();
        }
    }

    private PooledConnection createConnection(){
        try {
            return new PooledConnection(DriverManager.getConnection(URL, USER, PASSWORD));
        } catch (SQLException sqle){
            sqle.printStackTrace();
            throw new ConnectionCreationException();
        }
    }

    /**
     * Gets connection from connection pool, if pool is empty it will wait pool.freeConnectionWaitTime milliseconds defined in database.properties
     * @return Connection or throw {@link NoAvailableConnections} exception, if connection pool is empty
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.poll(FREE_CONNECTION_WAIT_TIME, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }
        if(connection == null){
            throw new NoAvailableConnections();
        }
        logger.debug("got connection");
        usedConnections.add(connection);
        return connection;
    }

    /**
     * Releases connection back to pool, if the connection was in usedConnections list,
     * or throws {@link ConnectionNotReturned} if connection was not returned to pool
     * @param connection Connection to release
     */
    public static void releaseConnection(Connection connection){
        if(usedConnections.remove(connection)) {
            if(pool.offer(connection)) {
                logger.debug("Returned connection back to pool");
            } else {
                logger.fatal("Could not return connection to connection poll");
                throw new ConnectionNotReturned();
            }
        } else {
            logger.fatal("Could not return connection to connection poll");
            throw new ConnectionNotReturned();
        }
    }

    private static void initializeProperties() {
        ResourceBundle properties = ResourceBundle.getBundle("database");
        URL = properties.getString("database.url");
        USER = properties.getString("database.user");
        PASSWORD = properties.getString("database.password");
        DRIVER_NAME = properties.getString("driver.name");
        POOL_SIZE = Integer.parseInt(properties.getString("pool.size"));
        FREE_CONNECTION_WAIT_TIME = Integer.parseInt(properties.getString("pool.freeConnectionWaitTime"));
    }

    public static void releaseAllConnections(){
        usedConnections.forEach(ConnectionPool::releaseConnection);
    }

    /**
     * Closes all jdbc connections, should be only used at application shutdown
     */
    public static void closeAllConnections(){
        pool.forEach(c -> {
            try {
                ((PooledConnection)c).closeInDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static int getCurrentPoolSize(){
        return pool.size();
    }

    public static int getUsedConnectionsSize(){
        return usedConnections.size();
    }
}
