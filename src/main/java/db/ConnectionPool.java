package db;

import exceptions.db.ConnectionCreationException;
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
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    //TODO write constants in jcc style
    private static String url;
    private static String user;
    private static String password;
    private static String driverName;
    private static int poolSize;
    private static int FREE_CONNECTION_WAIT_TIME;

    private static BlockingQueue<Connection> connectionPool;
    private static BlockingQueue<Connection> usedConnections;

    private static final ReentrantLock lock = new ReentrantLock();
    private static boolean POOL_INITIALIZED = false;

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
        POOL_INITIALIZED = true;
        logger.info("Connection pool initialized");
    }

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
                // TODO add this value to config
                Thread.sleep(FREE_CONNECTION_WAIT_TIME);
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
            if(connectionPool.offer(connection)) {
                logger.debug("Returned connection back to pool");
            } else {
                logger.fatal("Could not return connection to connection poll");
            }
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
        FREE_CONNECTION_WAIT_TIME = Integer.parseInt(properties.getString("pool.freeConnectionWaitTime"));
    }

    public static void releaseAllConnections(){
        usedConnections.forEach(ConnectionPool::releaseConnection);
    }

    public static void closeAllConnections(){
        connectionPool.forEach(c -> {
            try {
                ((PooledConnection)c).closeInDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static int getCurrentPoolSize(){
        return connectionPool.size();
    }

    public static int getUsedConnectionsSize(){
        return usedConnections.size();
    }
}
