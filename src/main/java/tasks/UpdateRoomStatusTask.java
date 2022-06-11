package tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RoomsService;
import tasks.base.Scheduled;
import tasks.base.ScheduledTask;

import java.util.concurrent.TimeUnit;

@Scheduled(period = 2, timeUnit = TimeUnit.HOURS)
public class UpdateRoomStatusTask implements ScheduledTask {

    private static final RoomsService roomService = RoomsService.getInstance();

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started updating rooms status task");
        int affectedRows = roomService.updateRoomsStatus();
        logger.info("Finished updating rooms status affected rows {}", affectedRows);
    }
}
