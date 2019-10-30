package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Deque;


public class GiftCertificatesSpecificationConjunction implements Specification<GiftCertificate> {

    private Deque<Specification<GiftCertificate>> specifications;

    public GiftCertificatesSpecificationConjunction(Deque<Specification<GiftCertificate>> specifications) {
        this.specifications = specifications;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<GiftCertificate> query, CriteriaBuilder criteriaBuilder) {
        recursiveCriteriaQuery(query, criteriaBuilder, this.specifications);
    }

    public void recursiveCriteriaQuery(CriteriaQuery<GiftCertificate> originalQuery, CriteriaBuilder builder,
                                       Deque<Specification<GiftCertificate>> specifications) {
        Specification<GiftCertificate> specification = specifications.poll();
        if (specification != null) {
            specification.setPredicatesIntoQuery(originalQuery, builder);
            recursiveCriteriaQuery(originalQuery, builder, specifications);
        }
    }
}
