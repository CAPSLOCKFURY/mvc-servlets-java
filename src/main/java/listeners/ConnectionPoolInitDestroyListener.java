package listeners;

import db.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class ConnectionPoolInitDestroyListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        ConnectionPool.initPool();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.releaseAllConnections();
        ConnectionPool.closeAllConnections();
    }
}
