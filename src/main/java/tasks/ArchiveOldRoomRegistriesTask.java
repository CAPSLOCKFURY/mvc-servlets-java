package tasks;

import service.RoomsService;

import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArchiveOldRoomRegistriesTask extends TimerTask {

    private final RoomsService roomsService = RoomsService.getInstance();

    private final static Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started archiving old room registries task");
        int affectedRows = roomsService.archiveOldRoomRegistries();
        logger.info("Finished archiving old room registries number of affected rows {}", affectedRows);
    }
}
