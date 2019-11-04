package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class SortGiftCertificatesByName extends SortSpecification<GiftCertificate> {

    private int sortOrder;

    public SortGiftCertificatesByName(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder) {
        if (sortOrder == 1) {
            criteriaQuery.orderBy(builder.asc(criteriaQuery.from(GiftCertificate.class).get("name")));
        } else {
            criteriaQuery.orderBy(builder.desc(criteriaQuery.from(GiftCertificate.class).get("name")));
        }
    }
}

