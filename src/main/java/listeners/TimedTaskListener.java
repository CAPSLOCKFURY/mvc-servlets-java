package listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionListener;
import tasks.base.Scheduled;
import tasks.base.ScheduledTask;
import utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@WebListener
public class TimedTaskListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        createTasks(getTimedTaskClasses()).forEach(task -> {
            Scheduled scheduled = task.getClass().getAnnotation(Scheduled.class);
            scheduler.scheduleAtFixedRate(task::run, scheduled.initialDelay(), scheduled.period(), scheduled.timeUnit());
        });
    }

    public List<Class<?>> getTimedTaskClasses(){
        return ClassUtils.getClassesInPackage("tasks",
                c -> c.isAnnotationPresent(Scheduled.class) && ScheduledTask.class.isAssignableFrom(c));
    }

    public List<ScheduledTask> createTasks(List<Class<?>> timedTaskClasses){
        return timedTaskClasses.stream().map(c -> {
            try {
                return (ScheduledTask) c.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }

}
