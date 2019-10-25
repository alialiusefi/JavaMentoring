package com.epam.esm.repository.specification;


import com.epam.esm.entity.AbstractEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public abstract interface Specification<T extends AbstractEntity> {

    CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder);

    /*Object[] getParameters();*/

}
