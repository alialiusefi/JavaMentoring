package com.epam.esm.repository.specification;


import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class FindAllOrdersByUserID extends FindSpecification<Order> {

    private Long userID;

    public FindAllOrdersByUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Order> criteriaQuery, CriteriaBuilder builder) {
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.where(builder.equal(userRoot.get("id"), this.userID));
        Join<User, Order> orders = userRoot.join("orderList");
        criteriaQuery.select(orders);
    }
}
