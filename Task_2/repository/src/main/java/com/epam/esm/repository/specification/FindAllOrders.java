package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllOrders extends FindSpecification<Order> {


    @Override
    public Query getQuery(EntityManager entityManager,
                          CriteriaBuilder builder) {
        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery);
    }
}
