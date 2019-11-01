package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "orders")
public class Order extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "tagSequence", sequenceName = "tag_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSequence")
    private Long id;

    @Column(name = "ordercost", nullable = false)
    private BigDecimal orderCost;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @OneToMany
    @JoinTable(name = "order_giftcertificate", joinColumns =
            {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "giftcertificate_id")})
    private List<GiftCertificate> giftCertificateList;

    @OneToOne
    @JoinTable(name = "order_user", joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private User user;

    private Order(OrderBuilder builder) {
        this.id = builder.id;
        this.orderCost = builder.orderCost;
        this.timestamp = builder.timestamp;
        this.user = builder.user;
        this.giftCertificateList = builder.giftCertificateList;
    }

    public Order() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderCost, order.orderCost) &&
                Objects.equals(timestamp, order.timestamp) &&
                Objects.equals(giftCertificateList, order.giftCertificateList) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderCost, timestamp, giftCertificateList, user);
    }

    public static class OrderBuilder {

        private final Long id;
        private final BigDecimal orderCost;
        private final LocalDateTime timestamp;
        private final List<GiftCertificate> giftCertificateList;
        private final User user;

        public OrderBuilder(Long id, BigDecimal orderCost,
                            LocalDateTime timestamp, User user, List<GiftCertificate> giftCertificateList) {
            this.id = id;
            this.orderCost = orderCost;
            this.timestamp = timestamp;
            this.user = user;
            this.giftCertificateList = giftCertificateList;
        }

        public Order getResult() {
            return new Order(this);
        }

    }

}
