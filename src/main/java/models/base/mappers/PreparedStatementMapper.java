package models.base.mappers;

import java.sql.PreparedStatement;

public interface PreparedStatementMapper {
    //TODO put prepared statement here as parameter
    void mapToPreparedStatement(PreparedStatement stmt);
}
