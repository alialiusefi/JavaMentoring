package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Validated
public class OrderDTO extends DTO {

    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

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

    public OrderDTO(Long id, @NotBlank @Size(min = 4, max = 50) String username,
                    @NotNull @Positive BigDecimal orderCost, @NotNull LocalDateTime timestamp,
                    @Valid List<GiftCertificateDTO> giftCertificatesOrdered) {
        super(id);
        this.username = username;
        this.orderCost = orderCost;
        this.timestamp = timestamp;
        this.giftCertificatesOrdered = giftCertificatesOrdered;
    }

    public OrderDTO() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(username, orderDTO.username) &&
                Objects.equals(orderCost, orderDTO.orderCost) &&
                Objects.equals(timestamp, orderDTO.timestamp) &&
                Objects.equals(giftCertificatesOrdered, orderDTO.giftCertificatesOrdered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, orderCost, timestamp, giftCertificatesOrdered);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orderCost=" + orderCost +
                ", timestamp=" + timestamp +
                ", giftCertificateList=" + giftCertificatesOrdered +
                ", id=" + id +
                '}';
    }
}
