package tasks.base;

public interface ScheduledTask {

    default void init(){

    }

    void run();
}
