package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Deque;

public class GiftCertificatesSpecificationConjunction extends FindSpecification<GiftCertificate> {

    private Deque<Specification<GiftCertificate>> specifications;

    public GiftCertificatesSpecificationConjunction(Deque<Specification<GiftCertificate>> specifications) {
        this.specifications = specifications;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return null;
    }
}
