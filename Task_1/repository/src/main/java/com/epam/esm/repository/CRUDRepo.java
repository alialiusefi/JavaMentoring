package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class CRUDRepo<T extends Entity> implements ICRUDRepo<T> {

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;

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

    protected abstract Object[] getFieldsArray(T entity);
}
