package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"id"})
public class AddOrderDTO extends DTO {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timestamp;

    @Valid
    private List<@Positive @NotNull Long> giftCertificates;

    public AddOrderDTO() {
        super();
    }

    public AddOrderDTO(Long id,
                       @NotNull LocalDateTime timestamp, @Valid List<Long> giftCertificatesOrdered) {
        super(id);
        this.timestamp = timestamp;
        this.giftCertificates = giftCertificatesOrdered;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<Long> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<Long> giftCertificatesOrdered) {
        this.giftCertificates = giftCertificatesOrdered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AddOrderDTO orderDTO = (AddOrderDTO) o;
        return
                Objects.equals(timestamp, orderDTO.timestamp) &&
                        Objects.equals(giftCertificates, orderDTO.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timestamp, giftCertificates);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                ", orderCost=" +
                ", timestamp=" + timestamp +
                ", giftCertificatesOrdered=" + giftCertificates +
                ", id=" + id +
                '}';
    }
}