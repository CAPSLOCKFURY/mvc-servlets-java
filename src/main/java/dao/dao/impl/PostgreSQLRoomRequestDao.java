package dao.dao.impl;

import dao.dao.RoomRequestDao;
import db.ConnectionPool;
import forms.RoomRequestForm;
import models.RoomRequest;
import models.User;
import models.base.SqlColumn;
import models.base.SqlType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomRequestDao extends RoomRequestDao {

    private final static String INSERT_ROOM_REQUEST = "insert into room_requests (user_id, capacity, room_class, check_in_date, check_out_date, comment)\n" +
            "values (?, ?, ?, ?, ?, ?)";

    private final static String FIND_ROOM_REQUESTS_BY_USER_ID = "select room_requests.*, rct.name as class_name from room_requests\n" +
            "    left outer join room_class_translation rct on rct.class_id = room_requests.room_class and rct.language = ?\n" +
            "    where user_id = ? order by -room_requests.id";

    @Override
    public boolean createRoomRequest(RoomRequestForm form) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            return createEntity(connection, INSERT_ROOM_REQUEST, form);
        }
    }

    @Override
    public List<RoomRequest> getAllRoomRequestsByUserId(Long userId, String locale) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long id = userId;
                private String getLang(){return lang;}
                public Long getId() {return id;}
            }
            return getAllByParams(connection, FIND_ROOM_REQUESTS_BY_USER_ID, new Param(), RoomRequest.class);
        }
    }
}
