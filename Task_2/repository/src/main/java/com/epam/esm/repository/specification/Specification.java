package com.epam.esm.repository.specification;


import com.epam.esm.entity.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public interface Specification<T extends AbstractEntity> {

    Query getQuery(EntityManager em, CriteriaBuilder builder);


}
