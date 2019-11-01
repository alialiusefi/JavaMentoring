package com.epam.esm.repository.specification;

import com.epam.esm.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindUserByUserID extends FindSpecification<User> {

    private Long id;

    public FindUserByUserID(Long id) {
        this.id = id;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<User> criteriaQuery, CriteriaBuilder builder) {
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(root.get("id"), this.id));
    }
}
