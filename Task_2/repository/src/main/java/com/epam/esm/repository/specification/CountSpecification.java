package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public interface CountSpecification extends Specification {

    Query getQuery(EntityManager em, CriteriaBuilder builder);
}
