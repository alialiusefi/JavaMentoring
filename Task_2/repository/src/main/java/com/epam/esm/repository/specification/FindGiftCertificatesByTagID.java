package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FindGiftCertificatesByTagID extends FindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ? ";
    private static final String CONJ_SQL_CLAUSE = "inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ?";

    private Long[] tagID;

    public FindGiftCertificatesByTagID(Long[] id) {
        this.tagID = id;
    }


    @Override
    public void setPredicatesIntoQuery(CriteriaQuery<GiftCertificate> criteriaQuery, CriteriaBuilder builder) {
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        Predicate[] predicates = new Predicate[tagID.length];
        for (int i = 0; i < predicates.length; i++) {
            predicates[i] = builder.equal(tagRoot.get("id"), tagID[i]);
        }
        criteriaQuery.where(builder.or(predicates));
        Join<Tag, GiftCertificate> tags = tagRoot.join("certificateList");
        criteriaQuery.select(tags);
    }
}