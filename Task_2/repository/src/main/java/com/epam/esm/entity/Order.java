package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "ordercost", nullable = false)
    private BigDecimal orderCost;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToMany
    @JoinTable(name = "order_giftcertificate", joinColumns =
            {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "giftcertificate_id")})
    private List<GiftCertificate> giftCertificateList;

    private Order(OrderBuilder builder) {
        this.id = builder.id;
        this.userID = builder.userID;
        this.orderCost = builder.orderCost;
        this.timestamp = builder.timestamp;
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(userID, order.userID) &&
                Objects.equals(orderCost, order.orderCost) &&
                Objects.equals(timestamp, order.timestamp) &&
                Objects.equals(giftCertificateList, order.giftCertificateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, orderCost, timestamp, giftCertificateList);
    }

    public static class OrderBuilder {

        private final Long id;
        private final Long userID;
        private final BigDecimal orderCost;
        private final LocalDateTime timestamp;
        private final List<GiftCertificate> giftCertificateList;

        public OrderBuilder(Long id, Long userID, BigDecimal orderCost,
                            LocalDateTime timestamp, List<GiftCertificate> giftCertificateList) {
            this.id = id;
            this.userID = userID;
            this.orderCost = orderCost;
            this.timestamp = timestamp;
            this.giftCertificateList = giftCertificateList;
        }

        public Order getResult() {
            return new Order(this);
        }

    }

}
