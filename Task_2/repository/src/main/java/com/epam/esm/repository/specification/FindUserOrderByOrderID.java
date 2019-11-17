package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

public class FindUserOrderByOrderID extends FindSpecification<Order> {

    private Long userID;
    private Long orderID;

    public FindUserOrderByOrderID(Long userID, Long orderID) {
        this.userID = userID;
        this.orderID = orderID;
    }

    @Override
    public Query getQuery(EntityManager manager, CriteriaBuilder builder) {
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        Predicate[] predicates = new Predicate[]{
                builder.equal(userRoot.get("id"), this.userID),
                builder.equal(orderRoot.get("id"), this.orderID)};
        criteriaQuery.where(builder.and(predicates));
        Join<UserEntity, Order> orders = userRoot.join("orders");
        criteriaQuery.select(orders);
        return manager.createQuery(criteriaQuery);
    }
}
