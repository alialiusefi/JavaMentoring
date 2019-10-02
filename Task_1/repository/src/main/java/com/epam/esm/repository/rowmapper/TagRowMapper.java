package com.epam.esm.repository.rowmapper;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public final class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Tag(
                resultSet.getInt("id"),
                resultSet.getString("tag_name"));
    }

}
