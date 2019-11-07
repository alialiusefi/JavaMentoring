package com.epam.esm.repository.specification;

import com.epam.esm.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindUserByUserID extends FindSpecification<UserEntity> {

    private Long id;

    public FindUserByUserID(Long id) {
        this.id = id;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<UserEntity> criteriaQuery, CriteriaBuilder builder) {
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(builder.equal(root.get("id"), this.id));
    }
}
