package listeners;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import tasks.ArchiveOldRoomRegistriesTask;
import tasks.DeleteOldBillingsTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class TimedTaskListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new DeleteOldBillingsTask(), 0, 24, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(new ArchiveOldRoomRegistriesTask(), 0, 24, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

}
