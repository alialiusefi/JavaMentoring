package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByName implements NativeSQLFindSpecification<GiftCertificate> {
    // fix sql bracket
    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry,giftcertificates.isforsale " +
            "from giftcertificates " +
            "where (public.consists(?,giftcertificates.name) and giftcertificates.isforsale = true ";

    private static final String CONJ_SQL_CLAUSE = " and public.consists(?,giftcertificates.name) ";

    private String name;

    public FindGiftCertificatesByName(String name) {
        this.name = name;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        String finalQuery = SQL_CLAUSE + " ) ";
        Query nativeQuery = em.createNativeQuery(finalQuery);
        nativeQuery.setParameter(1, name);
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_CLAUSE : SQL_CLAUSE;
    }

}
