package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindTagByName extends FindSpecification<Tag> {

    private String name;

    public FindTagByName(String name) {
        this.name = name;
    }

    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<Tag> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"),this.name));
    }
}
