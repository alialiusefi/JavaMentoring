package com.java.esm.repository;

import com.java.esm.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class CRUDRepo<T extends Entity> implements ICRUDRepo<T> {

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper rowMapper;


    @Autowired
    protected CRUDRepo(JdbcTemplate template, RowMapper<T> rowMapper)
    {
        this.jdbcTemplate = template;
        this.rowMapper = rowMapper;
    }

    @Autowired
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public RowMapper getRowMapper() {
        return rowMapper;
    }

    @Autowired
    public void setRowMapper(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    abstract protected Object[] getFieldsArray(T entity);
}
