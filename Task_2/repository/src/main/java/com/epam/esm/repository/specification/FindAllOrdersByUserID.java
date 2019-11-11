package com.epam.esm.repository.specification;


import com.epam.esm.entity.Order;
import com.epam.esm.entity.UserEntity;

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
    public void getQuery(CriteriaQuery<Order> criteriaQuery, CriteriaBuilder builder) {
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        criteriaQuery.where(builder.equal(userRoot.get("id"), this.userID));
        Join<UserEntity, Order> orders = userRoot.join("orderList");
        criteriaQuery.select(orders);
    }
}
