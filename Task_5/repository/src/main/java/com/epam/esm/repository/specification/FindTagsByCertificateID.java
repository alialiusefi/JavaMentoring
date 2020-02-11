package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FindTagsByCertificateID extends FindSpecification<Tag> {

    private static final String SQL_CLAUSE = "select tag.id, tag.tag_name " +
            "from tag inner join tagged_giftcertificates " +
            "on tag.id = tagged_giftcertificates.tag_id " +
            "where gift_certificate_id = ? ";
    private static final String CONJ_SQL_CLAUSE = "and where gift_certificate_id = ? ";
    private long certificateID;

    public FindTagsByCertificateID(long certificateID) {
        this.certificateID = certificateID;
    }


    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        CriteriaQuery<Tag> criteriaQuery = builder.createQuery(Tag.class);
        Root<GiftCertificate> certificateRoot = criteriaQuery.from(GiftCertificate.class);
        Predicate[] predicates = new Predicate[]{
                builder.equal(certificateRoot.get("id"), this.certificateID)};
        criteriaQuery.where(predicates);
        Join<GiftCertificate, Tag> tags = certificateRoot.join("tags");
        criteriaQuery.select(tags);
        return em.createQuery(criteriaQuery);
    }
}
