package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindOrderByID extends FindSpecification<Order> {

    private Long id;

    public FindOrderByID(Long id) {
        this.id = id;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Order> criteriaQuery, CriteriaBuilder builder) {
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(builder.equal(root.get("id"), this.id));
    }
}
