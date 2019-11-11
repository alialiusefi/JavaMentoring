package com.epam.esm.repository.specification;

import com.epam.esm.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindUserByUserID extends FindSpecification<UserEntity> {

    private Long id;

    public FindUserByUserID(Long id) {
        this.id = id;
    }

    @Override
    public Query getQuery(EntityManager manager, CriteriaBuilder builder) {
        CriteriaQuery<UserEntity> criteriaQuery = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(builder.equal(root.get("id"), this.id));
        return manager.createQuery(criteriaQuery);
    }
}
