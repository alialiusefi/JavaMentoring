package com.epam.esm.repository.specification;

import com.epam.esm.entity.ImageMetadataEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FindImageMetaDataByID extends FindSpecification<ImageMetadataEntity> {

    private Long id;

    public FindImageMetaDataByID(Long id) {
        this.id = id;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        CriteriaQuery<ImageMetadataEntity> criteriaQuery = builder.createQuery(ImageMetadataEntity.class);
        Root<ImageMetadataEntity> root = criteriaQuery.from(ImageMetadataEntity.class);
        Predicate predicate = builder.equal(root.get("id"), this.id);
        criteriaQuery.select(root).where(predicate);
        return em.createQuery(criteriaQuery);
    }
}
