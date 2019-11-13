package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Deque;

public class GiftCertificatesSpecificationConjunction extends FindSpecification<GiftCertificate> {

    private Deque<NativeSpecification<GiftCertificate>> specifications;

    public GiftCertificatesSpecificationConjunction(Deque<NativeSpecification<GiftCertificate>> specifications) {
        this.specifications = specifications;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        NativeSpecification<GiftCertificate> first = specifications.poll();
        String query = first.getSQLClause(false);
        StringBuilder stringBuilder = new StringBuilder(query);
        for (NativeSpecification i : specifications) {
            stringBuilder.append(i.getSQLClause(true));
        }
        String finalQuery = stringBuilder.toString();
        return em.createNativeQuery(finalQuery);
    }
}
