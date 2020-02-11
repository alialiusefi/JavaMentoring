package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Valid
    private List<@Positive @NotNull Long> giftCertificates;

    public AddOrderDTO() {
        super();
    }

    public AddOrderDTO(Long id,
                       @NotNull LocalDateTime timestamp, @Valid List<Long> giftCertificatesOrdered) {
        super(id);
        this.giftCertificates = giftCertificatesOrdered;
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

                Objects.equals(giftCertificates, orderDTO.giftCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificates);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                ", orderCost=" +
                ", giftCertificatesOrdered=" + giftCertificates +
                ", id=" + id +
                '}';
    }
}
