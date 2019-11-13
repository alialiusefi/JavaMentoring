package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindMostUsedTagOfMostExpensiveUserOrders extends NativeSQLFindSpecification<GiftCertificate> {

    public static final String SQL_CLAUSE = "select tag.id, tag.tag_name " +
            "from tag " +
            "where tag.id in (select tag_id " +
            "from tagged_giftcertificates ct " +
            "where ct.gift_certificate_id = (select gift_certificate_id " +
            "from ((select id " +
            "from order_user " +
            "where order_user.user_id = ?) as app_or " +
            "left join orders " +
            "on app_or.id = orders.id) as s " +
            "group by gift_certificate_id, s.id " +
            "order by sum(s.ordercost) desc " +
            "limit 1))";

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
