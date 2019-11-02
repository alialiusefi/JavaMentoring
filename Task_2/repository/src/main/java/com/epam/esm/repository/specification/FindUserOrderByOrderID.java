package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import javax.persistence.criteria.*;

public class FindUserOrderByOrderID extends FindSpecification<Order> {

    private Long userID;
    private Long orderID;

    public FindUserOrderByOrderID(Long userID, Long orderID) {
        this.userID = userID;
        this.orderID = orderID;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Order> criteriaQuery, CriteriaBuilder builder) {
        Root<User> userRoot = criteriaQuery.from(User.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Predicate[] predicates = new Predicate[]{
                builder.equal(userRoot.get("id"), this.userID),
                builder.equal(orderRoot.get("id"), this.orderID)};
        criteriaQuery.where(builder.and(predicates));
        Join<User, Order> orders = userRoot.join("orderList");
        criteriaQuery.select(orders);
    }
}
