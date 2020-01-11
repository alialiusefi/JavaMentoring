package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindAllGiftCertificates implements NativeSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry,giftcertificates.isforsale " +
            "from giftcertificates " +
            "where (giftcertificates.isforsale = 'true' ";

    private static final String CONJ_SQL_CLAUSE = " and giftcertificates.isforsale = true ";


    @Override
    public Query getQuery(EntityManager entityManager, CriteriaBuilder builder) {
        String finalQuery = SQL_CLAUSE + " ) ";
        Query nativeQuery = entityManager.createNativeQuery(finalQuery);
        return nativeQuery;
    }

    public String getSQLClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_CLAUSE : SQL_CLAUSE;
    }

}
