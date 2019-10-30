package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllTags extends FindSpecification<Tag> {

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Tag> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
    }
}
