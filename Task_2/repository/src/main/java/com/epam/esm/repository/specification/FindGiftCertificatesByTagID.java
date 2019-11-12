package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByTagID
        extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ? ";

    private static final String CONJ_SQL_CLAUSE = "inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ?";


    private Long[] tagID;
    private boolean isConjunction;

    public FindGiftCertificatesByTagID(Long[] tagID, boolean isConjunction) {
        this.tagID = tagID;
        this.isConjunction = isConjunction;
    }

    @Override
    public Query getQuery(EntityManager em,
                          CriteriaBuilder builder) {
        StringBuilder stringBuilder;
        if (!isConjunction) {
            stringBuilder = new StringBuilder(SQL_CLAUSE);
        } else {
            stringBuilder = new StringBuilder(CONJ_SQL_CLAUSE);
        }
        for (int i = 1; i < tagID.length; i++) {
            stringBuilder.append(" or tag_id = ?");
        }
        Query nativeQuery = em.createNativeQuery(stringBuilder.toString());
        for (int i = 0; i < tagID.length; i++) {
            nativeQuery.setParameter(i, tagID[i]);
        }

        return nativeQuery;
    }
}