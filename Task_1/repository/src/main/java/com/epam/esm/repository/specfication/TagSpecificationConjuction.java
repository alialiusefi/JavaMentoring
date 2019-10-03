package com.epam.esm.repository.specfication;

import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagSpecificationConjuction implements Specification<Tag> {

    public TagSpecificationConjuction(List<Specification<Tag>> specificationList) {
        this.specificationsToJoin = specificationList;
    }

    List<Specification<Tag>> specificationsToJoin;

    @Override
    public String toSqlClause(boolean isConjunction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(specificationsToJoin.get(0).toSqlClause(false));
        for (int i = 1; i < specificationsToJoin.size(); i++) {
            stringBuilder.append(specificationsToJoin.get(i).toSqlClause(true));
        }
        return stringBuilder.toString();
    }

    @Override
    public Object[] getParameters() {
        List<Object> parameters = new ArrayList<>();
        for (Specification<Tag> specification : specificationsToJoin) {
            parameters.addAll(Arrays.asList(specification.getParameters()));
        }
        return parameters.toArray();
    }
}
