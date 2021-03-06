package tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RoomsService;
import tasks.base.Scheduled;
import tasks.base.ScheduledTask;

import java.util.concurrent.TimeUnit;

@Scheduled(period = 24,timeUnit = TimeUnit.HOURS)
public class ArchiveOldRoomRegistriesTask implements ScheduledTask {

    private final RoomsService roomsService = RoomsService.getInstance();

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started archiving old room registries task");
        int affectedRows = roomsService.archiveOldRoomRegistries();
        logger.info("Finished archiving old room registries number of affected rows {}", affectedRows);
    }
}
