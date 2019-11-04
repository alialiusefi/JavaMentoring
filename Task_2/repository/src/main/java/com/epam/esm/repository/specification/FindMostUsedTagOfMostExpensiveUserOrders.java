package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class FindMostUsedTagOfMostExpensiveUserOrders extends FindSpecification<Tag> {

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Tag> criteriaQuery, CriteriaBuilder builder) {

    }

}
