package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByDescription implements NativeSQLFindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry,giftcertificates.isforsale " +
            "from giftcertificates " +
            "where (giftcertificates.isforsale = true and public.consists(?,giftcertificates.description)  ";

    private static final String CONJ_SQL_CLAUSE = " or public.consists(?,giftcertificates.description) " +
            "and giftcertificates.isforsale = true ";

    private String description;

    public FindGiftCertificatesByDescription(String description) {
        this.description = description;
    }


    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        String finalQuery = SQL_CLAUSE + " ) ";
        Query nativeQuery = em.createNativeQuery(finalQuery);
        nativeQuery.setParameter(1, description);
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_CLAUSE : SQL_CLAUSE;
    }
}
