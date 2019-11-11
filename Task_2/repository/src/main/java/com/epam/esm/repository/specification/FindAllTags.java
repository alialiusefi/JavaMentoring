package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllTags extends FindSpecification<Tag> {

    @Override
    public void getQuery(CriteriaQuery<Tag> criteriaQuery, CriteriaBuilder builder) {
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
    }
}
