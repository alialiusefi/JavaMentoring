package com.epam.esm.repository.specification;

import com.epam.esm.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllUsers extends FindSpecification<UserEntity> {

    @Override
    public void getQuery(CriteriaQuery<UserEntity> criteriaQuery, CriteriaBuilder builder) {
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        criteriaQuery.select(root);
    }

}
