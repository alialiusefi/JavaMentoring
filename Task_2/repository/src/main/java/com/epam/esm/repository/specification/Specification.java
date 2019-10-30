package com.epam.esm.repository.specification;


import com.epam.esm.entity.AbstractEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface Specification<T extends AbstractEntity> {

    void setPredicatesIntoQuery(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder);


}
