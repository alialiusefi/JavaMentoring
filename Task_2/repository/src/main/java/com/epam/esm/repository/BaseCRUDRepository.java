package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.repository.specification.Specification;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class BaseCRUDRepository<T extends AbstractEntity> implements CRUDRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseCRUDRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> queryEntity(Specification<T> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getCriteriaQuery(builder);
        specification.setPredicatesIntoQuery(criteriaQuery, builder);
        Query query = entityManager.createQuery(criteriaQuery);
        try {
            return Optional.of((T) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> queryList(Specification<T> specification, Integer pageNumber, Integer pageSize) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getCriteriaQuery(builder);
        specification.setPredicatesIntoQuery(criteriaQuery, builder);
        Query query = entityManager.createQuery(criteriaQuery);
        if (pageNumber != null && pageSize != null) {
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        return (List<T>) query.getResultList();
    }

    private CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return builder.createQuery(clazz);
    }
}