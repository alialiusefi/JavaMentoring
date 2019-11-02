package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllOrders extends FindSpecification<Order> {


    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Order> criteriaQuery,
                                       CriteriaBuilder builder) {
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
    }
}
