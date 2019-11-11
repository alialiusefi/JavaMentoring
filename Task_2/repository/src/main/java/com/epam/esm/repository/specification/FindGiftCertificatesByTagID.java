package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class FindGiftCertificatesByTagID
        extends NativeFindSpecification<GiftCertificate> {

    private static final String SQL_CLAUSE = "select giftcertificates.id,giftcertificates.name" +
            ",giftcertificates.description,giftcertificates.price" +
            ",giftcertificates.date_created,giftcertificates.date_modified," +
            "giftcertificates.duration_till_expiry " +
            "from giftcertificates inner join tagged_giftcertificates on giftcertificates.id = gift_certificate_id " +
            "where tag_id = ? ";

    private Long[] tagID;
    private String giftCertificateName;
    private String giftCertificateDesc;
    private Integer sortByDate;
    private Integer sortByName;

    public FindGiftCertificatesByTagID(Long[] tagID, String giftCertificateName,
                                       String GiftCertificateDesc,
                                       Integer sortByDate, Integer sortByName) {
        this.tagID = tagID;
        this.giftCertificateName = giftCertificateName;
        this.giftCertificateDesc = GiftCertificateDesc;
        this.sortByDate = sortByDate;
        this.sortByName = sortByName;
    }

    @Override
    public Query getQuery(EntityManager em,
                          CriteriaBuilder builder) {
        List<Predicate> predicates = new LinkedList<>();
        if (tagID != null) {
            Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
            Join<Tag, GiftCertificate> tags = tagRoot.join("certificateList");
            for (int i = 0; i < tagID.length; i++) {
                predicates.add(builder.equal(tagRoot.get("id"), tagID[i]));
            }

            criteriaQuery.select(tags);
        }

        if (giftCertificateName != null) {
            Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
            criteriaQuery.select(root).where(builder.like(root.get("name"), giftCertificateName));
        } else {
            if (giftCertificateDesc != null) {
                Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
                criteriaQuery.select(root).where(builder.like(root.get("description"), giftCertificateDesc));
            }
        }
        if (sortByDate != null && sortByDate != 0) {
            if (sortByDate != 1 && sortByDate != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            specifications.add(new SortGiftCertificatesByDate(sortByDate));

        } else {
            if (sortByName != null && sortByName != 0) {
                if (sortByName != 1 && sortByName != -1) {
                    throw new BadRequestException("Sort parameter should accept either 1 or -1");
                }
                specifications.add(new SortGiftCertificatesByName(sortByName));
            }
        }
        criteriaQuery.where(builder.or((Predicate[]) predicates.toArray()));


    }
}