package tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RoomsService;

import java.util.TimerTask;

public class UpdateRoomStatusTask extends TimerTask {

    private static final RoomsService roomService = RoomsService.getInstance();

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void run() {
        logger.info("Started updating rooms status task");
        int affectedRows = roomService.updateRoomsStatus();
        logger.info("Finished updating rooms status affected rows {}", affectedRows);
    }
}
