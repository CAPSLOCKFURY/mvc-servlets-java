package listeners;

import db.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

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
