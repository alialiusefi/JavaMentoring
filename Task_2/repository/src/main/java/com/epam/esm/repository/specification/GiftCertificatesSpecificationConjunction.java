package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Deque;
import java.util.List;

public class GiftCertificatesSpecificationConjunction extends FindSpecification<GiftCertificate> {

    private Deque<NativeSpecification<GiftCertificate>> specifications;
    private List<Object> parameters;

    public GiftCertificatesSpecificationConjunction(Deque<NativeSpecification<GiftCertificate>> specifications,
                                                    List<Object> parameters) {
        this.specifications = specifications;
        this.parameters = parameters;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        if (specifications.size() == 1) {
            return specifications.poll().getQuery(em, builder);
        }
        NativeSpecification<GiftCertificate> first = specifications.poll();
        String query = first.getSQLClause(false);
        StringBuilder stringBuilder = new StringBuilder(query);
        for (NativeSpecification i : specifications) {
            stringBuilder.append(i.getSQLClause(true));
        }
        String finalQuery = stringBuilder.toString();
        Query queryObj = em.createNativeQuery(finalQuery);
        for (int i = 1; i <= parameters.size(); i++) {
            queryObj.setParameter(i, parameters.get(i - 1));
        }
        return queryObj;
    }
}
