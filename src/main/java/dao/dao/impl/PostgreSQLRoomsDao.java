package dao.dao.impl;

import dao.dao.RoomsDao;
import db.ConnectionPool;
import models.Room;
import models.dto.RoomExtendedInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    private final static String FIND_ALL_ROOMS = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
            "       rooms.price as price, rooms.capacity as capacity, rc.name as class_name, rr.check_out_date from rooms\n" +
            "    left outer join room_registry rr on rooms.id = rr.room_id and archived = false\n" +
            "    left outer join room_class rc on rooms.class = rc.id\n" +
            "order by number;";

    private final static String FIND_ROOM_BY_ID = "select * from rooms where id = ?";

    @Override
    public List<RoomExtendedInfo> getAllRooms() throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, FIND_ALL_ROOMS, RoomExtendedInfo.class);
        }
    }

    @Override
    public Room getRoomById(Long id) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getOneById(connection, FIND_ROOM_BY_ID, id, Room.class);
        }
    }
}
