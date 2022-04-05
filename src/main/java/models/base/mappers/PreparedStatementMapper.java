package models.base.mappers;

import java.sql.PreparedStatement;

public interface PreparedStatementMapper {
    void mapToPreparedStatement(PreparedStatement stmt);
}
