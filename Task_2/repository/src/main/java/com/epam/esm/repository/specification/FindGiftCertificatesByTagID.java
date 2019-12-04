package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByTagID
        implements NativeSQLFindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry,giftcertificates.isforsale " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where (tag_id = ? ";

    private static final String CONJ_SQL_CLAUSE = "inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ?";


    private Long[] tagID;

    public FindGiftCertificatesByTagID(Long[] tagID) {
        this.tagID = tagID;
    }

    @Override
    public Query getQuery(EntityManager em,
                          CriteriaBuilder builder) {
        StringBuilder stringBuilder = new StringBuilder(SQL_CLAUSE);
        for (int i = 1; i < tagID.length; i++) {
            stringBuilder.append(" or tag_id = ?");
        }
        stringBuilder.append(" ) ");
        Query nativeQuery = em.createNativeQuery(stringBuilder.toString());
        for (int i = 0; i < tagID.length; i++) {
            nativeQuery.setParameter(i + 1, tagID[i]);
        }
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        String query = SQL_CLAUSE;
        if (isConjunction) {
            query = CONJ_SQL_CLAUSE;
        }
        StringBuilder stringBuilder = new StringBuilder(query);
        for (int i = 1; i < tagID.length; i++) {
            stringBuilder.append(" or tag_id = ?");
        }
        stringBuilder.append(" ) ");
        return stringBuilder.toString();
    }
}