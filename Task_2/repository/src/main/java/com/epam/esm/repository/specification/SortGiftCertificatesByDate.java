package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class SortGiftCertificatesByDate extends SortSpecification<GiftCertificate> {

    private int sortOrder;

    public SortGiftCertificatesByDate(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Query getQuery(EntityManager manager, CriteriaBuilder builder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = builder.createQuery(GiftCertificate.class);
        if (sortOrder == 1) {
            criteriaQuery.orderBy(builder.asc(criteriaQuery.from(GiftCertificate.class).get("dateOfCreation")));
        } else {
            criteriaQuery.orderBy(builder.desc(criteriaQuery.from(GiftCertificate.class).get("dateOfCreation")));
        }
        return manager.createQuery(criteriaQuery);
    }
}
