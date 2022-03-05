package dao.dao.impl;

import dao.dao.RoomsDao;
import db.ConnectionPool;
import models.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    private final static String FIND_ALL_ROOMS = "select * from rooms";


    @Override
    public List<Room> getAllRooms() throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return getAll(connection, FIND_ALL_ROOMS, Room.class);
        }
    }
}
