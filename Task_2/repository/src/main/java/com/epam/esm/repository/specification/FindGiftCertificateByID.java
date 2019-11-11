package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindGiftCertificateByID extends FindSpecification<GiftCertificate> {

    private Long id;

    public FindGiftCertificateByID(Long id) {
        this.id = id;
    }

    @Override
    public Query getQuery(EntityManager manager, CriteriaBuilder builder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root).where(builder.equal(root.get("id"), this.id));
        return manager.createQuery(criteriaQuery);
    }
}
