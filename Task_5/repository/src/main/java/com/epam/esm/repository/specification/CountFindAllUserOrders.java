package com.epam.esm.repository.specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class CountFindAllUserOrders implements CountSpecification {

    private static final String SQL_QUERY =
            "select count(g.id) from orders " +
                    " inner join order_giftcertificate og on orders.id = og.order_id " +
                    " inner join giftcertificates g on og.giftcertificate_id = g.id " +
                    " inner join order_user ou on orders.id = ou.order_id where user_id = ?;";

    private Long user_id;

    public CountFindAllUserOrders(Long user_id) {
        this.user_id = user_id;
    }


    public static String getSqlQuery() {
        return SQL_QUERY;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public Query getQuery(EntityManager em, CriteriaBuilder builder) {
        Query query = em.createNativeQuery(SQL_QUERY);
        query.setParameter(1, this.user_id);
        return query;
    }

}
