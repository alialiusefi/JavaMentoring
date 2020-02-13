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
        String finalQuery = getFinalQuery(em, builder);
        Query queryObj = em.createNativeQuery(finalQuery);
        for (int i = 0; i < parameters.size(); i++) {
            queryObj.setParameter(i + 1, parameters.get(i));
        }
        return queryObj;
    }

    public String getFinalQuery(EntityManager em, CriteriaBuilder builder) {
        NativeSpecification<GiftCertificate> first = specifications.poll();
        String query = first.getSQLClause(false);
        StringBuilder stringBuilder = new StringBuilder(query);
        for (NativeSpecification i : specifications) {
            stringBuilder.append(i.getSQLClause(true));
        }
        int lastBracket = stringBuilder.lastIndexOf(")");
        if (lastBracket != -1) {
            stringBuilder.insert(lastBracket + 1, " ) ");
        }
        try {
            String remainder = first.getRemainder();
            int idx_order = stringBuilder.indexOf("order");
            if (idx_order == -1) {
                return stringBuilder.toString();
            }
            stringBuilder.insert(idx_order, remainder);
            return stringBuilder.toString();
        } catch (UnsupportedOperationException e) {
            return stringBuilder.toString();
        }
    }

    public List<Object> getParameters() {
        return parameters;
    }


    public Deque<NativeSpecification<GiftCertificate>> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Deque<NativeSpecification<GiftCertificate>> specifications) {
        this.specifications = specifications;
    }
}
