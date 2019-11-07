package com.epam.esm.repository.specification;

import com.epam.esm.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindUserByUserName extends FindSpecification<UserEntity> {

    private String username;

    public FindUserByUserName(String username) {
        this.username = username;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<UserEntity> criteriaQuery, CriteriaBuilder builder) {
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(builder.equal(root.get("username"), this.username));
    }
}
