package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class CountGiftCertificatesSpecificationConjunction implements CountSpecification {

    private static final String COUNT_PREFIX = "select count(*) from ( ";
    private static final String COUNT_SUFFIX = " ) ) ) as foo";
    private GiftCertificatesSpecificationConjunction conjunction;

    public CountGiftCertificatesSpecificationConjunction(GiftCertificatesSpecificationConjunction specificationConjunction) {
        this.conjunction = specificationConjunction;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        List<Object> parameters = this.conjunction.getParameters();
        String query = conjunction.getFinalQuery(em, builder);
        StringBuilder stringBuilder = new StringBuilder(query);
        stringBuilder.insert(0, COUNT_PREFIX);
        stringBuilder.append(COUNT_SUFFIX);
        String finalQuery = stringBuilder.toString();
        Query queryObj = em.createNativeQuery(finalQuery);
        for (int i = 0; i < parameters.size(); i++) {
            queryObj.setParameter(i + 1, parameters.get(i));
        }
        return queryObj;
    }

}
