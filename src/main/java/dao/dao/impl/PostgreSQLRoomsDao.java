package dao.dao.impl;

import dao.dao.RoomsDao;
import db.ConnectionPool;
import models.RoomClass;
import models.base.SqlColumn;
import models.base.SqlType;
import models.dto.RoomExtendedInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLRoomsDao extends RoomsDao {

    //TODO add dynamic ordering
    private final static String FIND_ALL_ROOMS = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
            "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name, rr.check_out_date from rooms\n" +
            "    left outer join room_registry rr on rooms.id = rr.room_id and archived = false\n" +
            "    left outer join room_class rc on rooms.class = rc.id\n" +
            "    left outer join room_class_translation rct on rc.id = rct.class_id and language = ?\n" +
            "order by rc.id";

    private final static String FIND_ROOM_BY_ID = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
            "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name, rr.check_out_date from rooms\n" +
            "    left outer join room_registry rr on rooms.id = rr.room_id and archived = false\n" +
            "    left outer join room_class_translation rct on rooms.class = rct.class_id and language = ?\n" +
            "where rooms.id = ?";

    private final static String FIND_ALL_ROOM_CLASSES = "select class_id as id, name from room_class_translation where language = ?";

    @Override
    public List<RoomExtendedInfo> getAllRooms(String locale) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, FIND_ALL_ROOMS, new Param(), RoomExtendedInfo.class);
        }
    }

    @Override
    public RoomExtendedInfo getRoomById(Long id, String locale) throws SQLException {
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                @SqlColumn(columnName = "", type = SqlType.LONG)
                private final Long entityId = id;
                public String getLang() {return lang;}
                public Long getEntityId() {return entityId;}
            }
            return getOneByParams(connection, FIND_ROOM_BY_ID, new Param(), RoomExtendedInfo.class);
        }
    }

    @Override
    public List<RoomClass> getAllRoomClasses(String locale) throws SQLException{
        try(Connection connection = ConnectionPool.getConnection()){
            class Param{
                @SqlColumn(columnName = "", type = SqlType.STRING)
                private final String lang = locale;
                public String getLang() {return lang;}
            }
            return getAllByParams(connection, FIND_ALL_ROOM_CLASSES, new Param(), RoomClass.class);
        }
    }
}
