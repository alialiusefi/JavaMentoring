package com.epam.esm.repository.specification;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class FindMostUsedTagOfMostExpensiveUserOrders extends FindSpecification<GiftCertificate> {

    public static final String SQL_CLAUSE = "select tag.id, tag.tag_name " +
            "from tag " +
            "where tag.id in (select tag_id " +
            "from tagged_giftcertificates ct " +
            "where ct.gift_certificate_id = (select gift_certificate_id " +
            "from ((select id " +
            "from order_user " +
            "where order_user.user_id = ?) as app_or " +
            "left join order_giftcertificate " +
            "on app_or.id = order_giftcertificate.order_id) as s " +
            "group by gift_certificate_id, order_id " +
            "order by sum(select orders.ordercost from orders where orders.id = order_id) desc " +
            "limit 1))";

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        return em.createNativeQuery(SQL_CLAUSE);
    }
}
