package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class SortGiftCertificatesByName extends NativeSQLSortSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE_ASC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.name asc ";
    private static final String SQL_CLAUSE_DESC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.name desc ";

    private static final String CONJ_SQL_CLAUSE_ASC = "order by giftcertificates.name asc ";
    private static final String CONJ_SQL_CLAUSE_DESC = "order by giftcertificates.name desc ";

    private int sortOrder;

    public SortGiftCertificatesByName(int sortOrder) {
        this.sortOrder = sortOrder;
    }


    @Override
    public String getSQLClause(boolean isConjunction) {
        if (sortOrder == 1) {
            return isConjunction ? CONJ_SQL_CLAUSE_ASC : SQL_CLAUSE_ASC;
        } else {
            if (sortOrder == -1) {
                return isConjunction ? CONJ_SQL_CLAUSE_DESC : SQL_CLAUSE_DESC;
            }
        }
        return SQL_CLAUSE_ASC;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return sortOrder == 1 ? em.createNativeQuery(SQL_CLAUSE_ASC) : em.createNativeQuery(SQL_CLAUSE_DESC);
    }
}
