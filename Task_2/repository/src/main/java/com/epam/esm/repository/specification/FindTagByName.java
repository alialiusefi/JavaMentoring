package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindTagByName extends FindSpecification<Tag> {

    private String name;

    public FindTagByName(String name) {
        this.name = name;
    }

    @Override
    public Query getQuery(EntityManager entityManager, CriteriaBuilder builder) {
        CriteriaQuery<Tag> criteriaQuery = builder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(builder.equal(root.get("name"), this.name));
        return entityManager.createQuery(criteriaQuery);
    }
}
