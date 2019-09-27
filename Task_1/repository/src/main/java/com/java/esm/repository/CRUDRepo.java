package com.java.esm.repository;

import com.java.esm.entity.Entity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class CRUDRepo<T extends Entity> implements ICRUDRepo<T> {

    protected JdbcTemplate jdbcTemplate;
    protected RowMapper rowMapper;

    protected CRUDRepo(JdbcTemplate template, RowMapper<T> rowMapper)
    {
        this.jdbcTemplate = template;
        this.rowMapper = rowMapper;
    }



}
