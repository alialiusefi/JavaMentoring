package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllGiftCertificates extends FindSpecification<GiftCertificate> {


    @Override
    public Query getQuery(EntityManager entityManager, CriteriaBuilder builder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery);
    }
}
