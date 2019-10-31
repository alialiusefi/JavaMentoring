package com.epam.esm.repository.specification;

import com.epam.esm.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindUserByUserName extends FindSpecification<User> {

    private String username;

    public FindUserByUserName(String username) {
        this.username = username;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<User> criteriaQuery, CriteriaBuilder builder) {
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(root.get("username"),this.username));
    }
}
