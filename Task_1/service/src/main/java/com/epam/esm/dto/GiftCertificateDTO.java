package com.epam.esm.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Validated
public class GiftCertificateDTO extends DTO {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    @Size(min = 1, max = 50)
    @NotNull
    private String name;
    @Size(min = 1, max = 256)
    @NotNull
    private String description;
    @Positive
    @NotNull
    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfCreation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfModification;
    private Integer durationTillExpiry;

    @Valid
    private List<TagDTO> tags;

    public GiftCertificateDTO() {
        super();
    }

    public GiftCertificateDTO(long id, String name, String description, BigDecimal price, LocalDate dateOfCreation,
                              LocalDate dateOfModification, Integer durationTillExpiry, List<TagDTO> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.durationTillExpiry = durationTillExpiry;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(SCALE, ROUNDING_MODE);
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getDateOfModification() {
        return dateOfModification;
    }

    public void setDateOfModification(LocalDate dateOfModification) {
        this.dateOfModification = dateOfModification;
    }

    public int getDurationTillExpiry() {
        return durationTillExpiry;
    }

    public void setDurationTillExpiry(int durationTillExpiry) {
        this.durationTillExpiry = durationTillExpiry;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDTO that = (GiftCertificateDTO) o;
        return getDurationTillExpiry() == that.getDurationTillExpiry() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getDateOfCreation(), that.getDateOfCreation()) &&
                Objects.equals(getDateOfModification(), that.getDateOfModification()) &&
                Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDescription(),
                getPrice(), getDateOfCreation(), getDateOfModification(), getDurationTillExpiry(), getTags());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GiftCertificateDTO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", dateOfCreation=").append(dateOfCreation);
        sb.append(", dateOfModification=").append(dateOfModification);
        sb.append(", durationTillExpiry=").append(durationTillExpiry);
        sb.append(", tagDTOList=").append(tags);
        sb.append('}');
        return sb.toString();
    }
}
