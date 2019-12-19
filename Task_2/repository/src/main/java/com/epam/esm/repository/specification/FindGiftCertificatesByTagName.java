package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindGiftCertificatesByTagName implements NativeSQLFindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry,giftcertificates.isforsale " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "inner join tag on tagged_giftcertificates.tag_id = tag.id " +
            "where (giftcertificates.isforsale = true and (public.consists(?,tag.tag_name) ";

    private static final String CONJ_SQL_CLAUSE = "inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "inner join tag on tagged_giftcertificates.tag_id = tag.id " +
            "where (public.consists(?,tag.tag_name)  ";

    private String[] tagName;

    public FindGiftCertificatesByTagName(String[] tagName) {
        this.tagName = tagName;
    }

    @Override
    public Query getQuery(EntityManager em,
                          CriteriaBuilder builder) {
        StringBuilder stringBuilder = new StringBuilder(SQL_CLAUSE);
        for (int i = 1; i < tagName.length; i++) {
            stringBuilder.append(" or public.consists(?,tag_name)");
        }
        stringBuilder.append(" ) )");
        Query nativeQuery = em.createNativeQuery(stringBuilder.toString());
        for (int i = 0; i < tagName.length; i++) {
            nativeQuery.setParameter(i + 1, tagName[i]);
        }
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        if (isConjunction) {
            return CONJ_SQL_CLAUSE;
        }
        return SQL_CLAUSE;
    }
}
