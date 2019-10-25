package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.repository.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class BaseCRUDRepository<T extends AbstractEntity> implements CRUDRepository<T> {

    /*protected JdbcTemplate jdbcTemplate;
    protected RowMapper<T> rowMapper;*/
    @PersistenceContext protected EntityManager entityManager;

    protected BaseCRUDRepository(/*JdbcTemplate template, RowMapper<T> rowMapper,*/ EntityManager entityManager) {
      /*  this.jdbcTemplate = template;
        this.rowMapper = rowMapper;*/
        this.entityManager = entityManager;
    }

/*
    @Autowired
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    protected abstract Object[] getFieldsArray(T entity);
*/

    @Override
    public T queryEntity(Specification<T> specification){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        Query query = entityManager.createQuery(criteriaQuery);
        return (T) query.getSingleResult();
    }

    @Override
    public List<T> queryList(Specification<T> specification, int pageNumber, int pageSize){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = specification.getCriteriaQuery(builder);
        Query query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return (List<T>) query.getResultList();
    }

}
