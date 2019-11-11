package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftCertificateSpecificationConjunction implements Specification<GiftCertificate> {

    List<Specification<GiftCertificate>> specificationsToJoin;

    public GiftCertificateSpecificationConjunction(List<Specification<GiftCertificate>> specificationList) {
        this.specificationsToJoin = specificationList;
    }

    public String toSqlClause(boolean isConjunction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(specificationsToJoin.get(0).toSqlClause(false));
        for (int i = 1; i < specificationsToJoin.size(); i++) {
            stringBuilder.append(specificationsToJoin.get(i).toSqlClause(true));
        }
        return stringBuilder.toString();
    }

    public Object[] getParameters() {
        List<Object> parameters = new ArrayList<>();
        for (Specification<GiftCertificate> specification : specificationsToJoin) {
            parameters.addAll(Arrays.asList(specification.getParameters()));
        }
        return parameters.toArray();
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return null;
    }
}
