package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public final class FindTagByID extends FindSpecification<Tag> {

    /*private static final String SQL_QUERY = "select tag.id, tag.tag_name from tag where tag.id = ?";
    private static final String CONJ_SQL_QUERY = "and where tag.id = ?";*/
    private Long id;

    public FindTagByID(Long id) {
        this.id = id;
    }

    @Override
    public CriteriaQuery<Tag> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"),this.id));
        return criteriaQuery;
    }

    /*@Override
    public Object[] getParameters() {
        return new Object[]{
                id
        };
    }*/


}
