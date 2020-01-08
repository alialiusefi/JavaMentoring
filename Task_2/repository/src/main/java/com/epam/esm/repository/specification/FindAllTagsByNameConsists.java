package com.epam.esm.repository.specification;

import com.epam.esm.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindAllTagsByNameConsists implements NativeSpecification<Tag> {

    private static final String SQL_QUERY = "select tag.id," +
            " tag.tag_name from tag where " +
            " public.consists(?,tag.tag_name)  ";


    private static final String CONJ_SQL_QUERY = " where " +
            " public.consists(?,tag.tag_name)  ";


    private String tagName;

    public FindAllTagsByNameConsists(String tagName) {
        this.tagName = tagName;
    }


    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        String finalQuery = SQL_QUERY;
        Query nativeQuery = em.createNativeQuery(finalQuery);
        nativeQuery.setParameter(1, tagName);
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        return isConjunction ? CONJ_SQL_QUERY : SQL_QUERY;
    }

}