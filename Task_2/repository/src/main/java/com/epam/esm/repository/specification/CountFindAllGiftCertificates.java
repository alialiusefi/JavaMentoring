package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class CountFindAllGiftCertificates implements CountSpecification {

    private final static String SQL_QUERY = "select count(*) from giftcertificates where giftcertificates.isforsale = true";

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return em.createNativeQuery(SQL_QUERY);
    }
}
