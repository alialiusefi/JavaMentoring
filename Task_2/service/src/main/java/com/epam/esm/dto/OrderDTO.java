package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Validated
public class OrderDTO extends DTO {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;


    @NotNull
    @Positive
    private BigDecimal orderCost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    @Valid
    private List<GiftCertificateDTO> giftCertificates;

    public OrderDTO() {
        super();
    }

    public OrderDTO(Long id, @NotNull @Positive BigDecimal orderCost,
                    @NotNull LocalDateTime timestamp, @Valid List<GiftCertificateDTO> giftCertificatesOrdered) {
        super(id);
        this.orderCost = orderCost;
        this.timestamp = timestamp;
        this.giftCertificates = giftCertificatesOrdered;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost.setScale(SCALE, ROUNDING_MODE);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<GiftCertificateDTO> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificateDTO> giftCertificatesOrdered) {
        this.giftCertificates = giftCertificatesOrdered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(orderCost, orderDTO.orderCost) &&
                Objects.equals(timestamp, orderDTO.timestamp) &&
                Objects.equals(giftCertificates, orderDTO.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderCost, timestamp, giftCertificates);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                ", orderCost=" + orderCost +
                ", timestamp=" + timestamp +
                ", giftCertificatesOrdered=" + giftCertificates +
                ", id=" + id +
                '}';
    }
}
