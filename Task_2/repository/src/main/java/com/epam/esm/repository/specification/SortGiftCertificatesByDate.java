package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class SortGiftCertificatesByDate extends SortSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE_ASC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.date_created asc ";
    private static final String SQL_CLAUSE_DESC = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "order by giftcertificates.date_created desc ";

    private static final String CONJ_SQL_CLAUSE_ASC = "order by giftcertificates.date_created asc ";
    private static final String CONJ_SQL_CLAUSE_DESC = "order by giftcertificates.date_created desc ";

    private int sortOrder;

    public SortGiftCertificatesByDate(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder) {
        return;
    }
}
