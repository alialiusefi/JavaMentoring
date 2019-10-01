package com.java.esm.repository;

import com.java.esm.entity.Tag;
import com.java.esm.repository.specfication.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public final class TagRepo extends CRUDRepo<Tag> {

    private static final String SQL_INSERT = "insert into tag (tag_name) values (?);";
    private static final String SQL_DELETE = "delete from tag where tag_name = ?";

    public TagRepo(JdbcTemplate template, RowMapper<Tag> rowMapper) {
        super(template, rowMapper);
    }

    @Override
    public void add(Tag entity) {
        jdbcTemplate.update(SQL_INSERT,getFieldsArray(entity));
    }

    @Override
    public List<Tag> query(Specification specification) {
        return jdbcTemplate.query(specification.toSqlClauses(),
                rowMapper,specification.getParameters());
    }

    @Override
    public void delete(Tag entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    @Override
    public void delete(Specification specification) {
        List<Tag> tagsToDelete = query(specification);
        for(Tag tag : tagsToDelete){
            jdbcTemplate.update(SQL_DELETE,tag.getId());
        }
    }

    @Override
    protected Object[] getFieldsArray(Tag entity) {
        return new Object[]{entity.getName()};
    }

}
