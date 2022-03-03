package listeners;

import db.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.releaseAllConnections();
        ConnectionPool.closeAllConnections();
    }
}
