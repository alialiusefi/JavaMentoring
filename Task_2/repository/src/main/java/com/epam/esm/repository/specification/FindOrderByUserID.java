package com.epam.esm.repository.specification;

import com.epam.esm.entity.Order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindOrderByUserID extends FindSpecification<Order> {

    private Long userID;

    public FindOrderByUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Order> criteriaQuery, CriteriaBuilder builder) {
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(builder.equal(root.get("userID"), this.userID));
    }
}
