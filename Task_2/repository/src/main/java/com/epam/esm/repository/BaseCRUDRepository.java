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
    public T add(T entity) {
        entityManager.persist(entity);
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> queryEntity(Specification<T> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getCriteriaQuery(builder);
        specification.setPredicatesIntoQuery(criteriaQuery, builder);
        Query query = entityManager.createQuery(criteriaQuery);
        try {
            T entity = (T) query.getSingleResult();
            return Optional.of(entity);
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
        List<T> results = (List<T>) query.getResultList();
        return results;
    }

    @Override
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        T managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    @Override
    public void delete(Specification specification) {
        List<T> certificatesToDelete = queryList(specification, null, null);
        for (T i : certificatesToDelete) {
            delete(i);
        }
    }

    private CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder builder) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return builder.createQuery(clazz);
    }
}