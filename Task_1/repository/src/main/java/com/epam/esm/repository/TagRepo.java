package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.specfication.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tagRepo")
public final class TagRepo extends CRUDRepo<Tag> {

    private static final String SQL_INSERT = "insert into tag (tag_name) values (?);";
    private static final String SQL_DELETE = "delete from tag where tag.id = ?";

    @Autowired
    public TagRepo(JdbcTemplate template, RowMapper<Tag> mapper) {
        super(template, mapper);
    }

    @Override
    public void add(Tag entity) {
        jdbcTemplate.update(SQL_INSERT, getFieldsArray(entity));
    }

    @Override
    public List<Tag> query(Specification specification) {
        return jdbcTemplate.query(specification.toSqlClause(),
                rowMapper, specification.getParameters());
    }

    @Override
    public void delete(Tag entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    @Override
    public void delete(Specification specification) {
        List<Tag> tagsToDelete = query(specification);
        for (Tag tag : tagsToDelete) {
            jdbcTemplate.update(SQL_DELETE, tag.getId());
        }
    }

    @Override
    protected Object[] getFieldsArray(Tag entity) {
        return new Object[]{entity.getName()};
    }

}
