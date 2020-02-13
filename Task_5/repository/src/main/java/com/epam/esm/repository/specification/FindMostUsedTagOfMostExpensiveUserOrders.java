package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindMostUsedTagOfMostExpensiveUserOrders implements NativeSQLFindSpecification<GiftCertificate> {

    public static final String SQL_CLAUSE =
            "SELECT tag.id, tag.tag_name " +
                    "FROM tag " +
                    "         JOIN tagged_giftcertificates ON tag.id = tagged_giftcertificates.tag_id " +
                    "         JOIN giftcertificates ON tagged_giftcertificates.gift_certificate_id = giftcertificates.id " +
                    "         JOIN order_giftcertificate ON giftcertificates.id = order_giftcertificate.giftcertificate_id " +
                    "         join orders on order_giftcertificate.giftcertificate_id = orders.id " +
                    "         join order_user on orders.id = order_user.order_id " +
                    "WHERE order_user.user_id = ? " +
                    "GROUP BY tag.id " +
                    "ORDER BY sum(orders.ordercost) " +
                    "    DESC ";

    private Long userID;

    public FindMostUsedTagOfMostExpensiveUserOrders(Long userID) {
        this.userID = userID;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        Query nativeQuery = em.createNativeQuery(SQL_CLAUSE);
        nativeQuery.setParameter(1, userID);
        return nativeQuery;
    }

    @Override
    public String getSQLClause(boolean isConjunction) {
        if (isConjunction) {
            throw new UnsupportedOperationException("Unimplemented Operation");
        }
        return SQL_CLAUSE;
    }
}
