package dao.dao.impl;

import constants.SqlQueries;
import dao.dao.RoomRegistryDAO;
import models.RoomRegistry;

import java.sql.Connection;

public class PostgreSQLRoomRegistryDAO extends RoomRegistryDAO {

    @Override
    public long createRoomRegistry(RoomRegistry roomRegistry) {
        return createEntityAndGetId(SqlQueries.RoomRegistry.CREATE_ROOM_REGISTRY,
                new Object[]{roomRegistry.getUserId(), roomRegistry.getRoomId(), roomRegistry.getCheckInDate(),
                        roomRegistry.getCheckOutDate(), roomRegistry.getArchived()});
    }

    @Override
    public boolean updateRoomRegistry(RoomRegistry roomRegistry) {
        return updateEntityById(SqlQueries.RoomRegistry.UPDATE_ROOM_REGISTRY, new Object[]{roomRegistry.getUserId(), roomRegistry.getRoomId(), roomRegistry.getCheckInDate(),
                roomRegistry.getCheckOutDate(), roomRegistry.getArchived()}, roomRegistry.getRoomId());
    }

    @Override
    public RoomRegistry getRoomRegistryById(Long id) {
        return getOneById(SqlQueries.RoomRegistry.GET_ROOM_REGISTRY_BY_ID, id, RoomRegistry.class);
    }

    public PostgreSQLRoomRegistryDAO(Connection connection) {
        super(connection);
    }

}
