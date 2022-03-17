package tasks;

import service.RoomsService;

import java.util.TimerTask;

public class ArchiveOldRoomRegistriesTask extends TimerTask {

    private final RoomsService roomsService = RoomsService.getInstance();

    @Override
    public void run() {
        roomsService.archiveOldRoomRegistries();
    }
}
