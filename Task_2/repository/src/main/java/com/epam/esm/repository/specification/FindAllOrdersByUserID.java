package com.epam.esm.repository.specification;


import com.epam.esm.entity.Order;
import com.epam.esm.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public Query getQuery(EntityManager entityManager, CriteriaBuilder builder) {
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(builder.equal(userRoot.get("id"), this.userID));
        Join<UserEntity, Order> orders = userRoot.join("orderList");
        criteriaQuery.select(orders);
        return entityManager.createQuery(criteriaQuery);
    }
}
