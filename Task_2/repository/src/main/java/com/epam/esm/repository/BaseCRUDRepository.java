package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class BaseCRUDRepository<T extends AbstractEntity> implements CRUDRepository<T> {

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;

    protected BaseCRUDRepository(JdbcTemplate template, RowMapper<T> rowMapper) {
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
