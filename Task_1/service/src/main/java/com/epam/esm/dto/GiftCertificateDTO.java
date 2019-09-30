package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class GiftCertificateDTO extends DTO {
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate dateOfCreation;
    private LocalDate dateOfModification;
    private int durationTillExpiry;

    public GiftCertificateDTO(long id, String name, String description, BigDecimal price, LocalDate dateOfCreation,
                              LocalDate dateOfModification, int durationTillExpiry) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.durationTillExpiry = durationTillExpiry;
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
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDTO that = (GiftCertificateDTO) o;
        return durationTillExpiry == that.durationTillExpiry &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(dateOfCreation, that.dateOfCreation) &&
                Objects.equals(dateOfModification, that.dateOfModification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, dateOfCreation, dateOfModification, durationTillExpiry);
    }

    @Override
    public String toString() {
        return "GiftCertificateDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfModification=" + dateOfModification +
                ", durationTillExpiry=" + durationTillExpiry +
                '}';
    }
}
