package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindGiftCertificateByID extends FindSpecification<GiftCertificate> {

    private Long id;

    public FindGiftCertificateByID(Long id) {
        this.id = id;
    }

    @Override
    public CriteriaQuery<GiftCertificate> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), this.id));
        return criteriaQuery;
    }
}
