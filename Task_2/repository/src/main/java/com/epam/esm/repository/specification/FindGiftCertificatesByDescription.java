package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindGiftCertificatesByDescription extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates " +
            "where public.consists(?,giftcertificates.description) ";

    private static final String CONJ_SQL_CLAUSE = "and public.consists(?,giftcertificates.description) ";

    private String description;

    public FindGiftCertificatesByDescription(String description) {
        this.description = description;
    }


    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder) {
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

    }
}
