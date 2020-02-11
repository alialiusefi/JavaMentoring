package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class CountFindAllGiftCertificates implements CountSpecification {

    private static final String SQL_QUERY_ISFORSALE_TRUE =
            "select count(*) from giftcertificates where giftcertificates.isforsale = true";

    private static final String SQL_QUERY =
            "select count(*) from giftcertificates";

    private boolean allGiftCertificates;

    public CountFindAllGiftCertificates() {
    }

    public CountFindAllGiftCertificates(boolean allGiftCertificates) {
        this.allGiftCertificates = allGiftCertificates;
    }


    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        if (allGiftCertificates) {
            return em.createNativeQuery(SQL_QUERY_ISFORSALE_TRUE);
        }
        return em.createNativeQuery(SQL_QUERY);

    }
}
