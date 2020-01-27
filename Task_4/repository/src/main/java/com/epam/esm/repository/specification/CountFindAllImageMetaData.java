package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class CountFindAllImageMetaData implements CountSpecification {

    private static final String SQL_QUERY =
            "select count(imagemeta.id) from imagemeta";


    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return em.createNativeQuery(SQL_QUERY);
    }
}
