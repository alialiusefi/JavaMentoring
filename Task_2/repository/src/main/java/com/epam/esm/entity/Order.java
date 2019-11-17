package com.epam.esm.entity;

import javax.persistence.*;
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

    @ManyToOne
    @JoinTable(name = "order_user", inverseJoinColumns = {@JoinColumn(name = "user_id")}
            , joinColumns = {@JoinColumn(name = "order_id")})
    private UserEntity userEntity;


    @Column(name = "ordercost", nullable = false)
    private BigDecimal orderCost;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @OneToMany
    @JoinTable(name = "order_giftcertificate", joinColumns =
            {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "giftcertificate_id")})
    private List<GiftCertificate> giftCertificates;


    private Order(OrderBuilder builder) {
        this.id = builder.id;
        this.orderCost = builder.orderCost;
        this.timestamp = builder.timestamp;
        this.userEntity = builder.userEntity;
        this.giftCertificates = builder.giftCertificateList;
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

    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderCost, order.orderCost) &&
                Objects.equals(timestamp, order.timestamp) &&
                Objects.equals(giftCertificates, order.giftCertificates) &&
                Objects.equals(userEntity, order.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderCost, timestamp, giftCertificates, userEntity);
    }

    public static class OrderBuilder {

        private final Long id;
        private final BigDecimal orderCost;
        private final LocalDateTime timestamp;
        private final List<GiftCertificate> giftCertificateList;
        private final UserEntity userEntity;

        public OrderBuilder(Long id, BigDecimal orderCost,
                            LocalDateTime timestamp, UserEntity userEntity, List<GiftCertificate> giftCertificateList) {
            this.id = id;
            this.orderCost = orderCost;
            this.timestamp = timestamp;
            this.userEntity = userEntity;
            this.giftCertificateList = giftCertificateList;
        }

        public Order getResult() {
            return new Order(this);
        }

    }

}
