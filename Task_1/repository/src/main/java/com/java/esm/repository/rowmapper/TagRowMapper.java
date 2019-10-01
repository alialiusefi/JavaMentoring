package com.java.esm.repository.rowmapper;

import com.java.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Tag(
                resultSet.getInt("id"),
                resultSet.getNString("tag_name"));
    }

}
