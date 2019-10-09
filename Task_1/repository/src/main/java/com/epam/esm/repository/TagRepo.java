package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.rowmapper.TagRowMapper;
import com.epam.esm.repository.specfication.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public final class TagRepo extends CRUDRepo<Tag> {

    private static final String SQL_INSERT = "insert into tag (tag_name) values (?) returning id,tag_name";
    private static final String SQL_DELETE = "delete from tag where tag.id = ?";
    private static final String SQL_SELECT_BY_ID = "select tag.id, tag.tag_name " +
            "from tag where tag.id = ?";

    @Autowired
    public TagRepo(JdbcTemplate template, TagRowMapper mapper) {
        super(template, mapper);
    }

    @Override
    public Tag add(Tag entity) {
        return jdbcTemplate.queryForObject(SQL_INSERT, getFieldsArray(entity), rowMapper);
    }

    @Override
    public void update(Tag entity) {
        throw new UnsupportedOperationException("Method not implemented yet!");
    }

    @Override
    public Tag findByID(long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id);
    }

    @Override
    public List<Tag> query(Specification specification) {
        return jdbcTemplate.query(specification.toSqlClause(false),
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

    @Autowired
    public void setTagRowMapper(TagRowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

}
