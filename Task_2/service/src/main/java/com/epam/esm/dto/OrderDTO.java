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
import java.time.LocalDateTime;
import java.util.List;

@Validated
public class OrderDTO extends DTO {

    @NotNull
    @Positive
    private Long userID;

    @NotNull
    @Positive
    private BigDecimal orderCost;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    @Valid
    private List<GiftCertificateDTO> giftCertificatesOrdered;

    public OrderDTO(Long id, @NotNull @Positive Long userID, @NotNull @Positive BigDecimal orderCost,
                    @NotNull LocalDateTime timestamp, @Valid List<GiftCertificateDTO> giftCertificatesOrdered) {
        super(id);
        this.userID = userID;
        this.orderCost = orderCost;
        this.timestamp = timestamp;
        this.giftCertificatesOrdered = giftCertificatesOrdered;
    }

    public OrderDTO() {
        super();
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

    public List<GiftCertificateDTO> getGiftCertificatesOrdered() {
        return giftCertificatesOrdered;
    }

    public void setGiftCertificatesOrdered(List<GiftCertificateDTO> giftCertificatesOrdered) {
        this.giftCertificatesOrdered = giftCertificatesOrdered;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "userID=" + userID +
                ", orderCost=" + orderCost +
                ", timestamp=" + timestamp +
                ", giftCertificatesOrdered=" + giftCertificatesOrdered +
                '}';
    }
}
