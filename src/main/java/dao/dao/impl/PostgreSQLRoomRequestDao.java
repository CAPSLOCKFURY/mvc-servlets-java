package dao.dao.impl;

import dao.dao.RoomRequestDao;
import db.ConnectionPool;
import forms.RoomRequestForm;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSQLRoomRequestDao extends RoomRequestDao {

    private final static String INSERT_ROOM_REQUEST = "insert into room_requests (user_id, capacity, room_class, check_in_date, check_out_date, comment)\n" +
            "values (?, ?, ?, ?, ?, ?)";

    @Override
    public boolean createRoomRequest(RoomRequestForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntity(connection, INSERT_ROOM_REQUEST, form);
        }
    }
}
