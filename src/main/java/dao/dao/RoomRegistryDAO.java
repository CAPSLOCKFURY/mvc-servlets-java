package dao.dao;

import dao.dao.base.OrderableAbstractDao;
import models.RoomRegistry;

import java.sql.Connection;

public abstract class RoomRegistryDAO extends OrderableAbstractDao {

    public abstract long createRoomRegistry(RoomRegistry roomRegistry);

    public abstract boolean updateRoomRegistry(RoomRegistry roomRegistry);

    public abstract RoomRegistry getRoomRegistryById(Long id);

    public RoomRegistryDAO(Connection connection) {
        super(connection);
    }
}
