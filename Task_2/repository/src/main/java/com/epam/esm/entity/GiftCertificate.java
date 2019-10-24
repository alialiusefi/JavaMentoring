package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public final class GiftCertificate extends Entity {

    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate dateOfCreation;
    private LocalDate dateOfModification;
    private Integer durationTillExpiry;

    public GiftCertificate() {
        super();
    }


    private GiftCertificate(GiftCertificateBuilder giftCertificateBuilder) {
        super(giftCertificateBuilder.id);
        this.name = giftCertificateBuilder.name;
        this.description = giftCertificateBuilder.description;
        this.price = giftCertificateBuilder.price;
        this.dateOfCreation = giftCertificateBuilder.dateOfCreation;
        this.dateOfModification = giftCertificateBuilder.dateOfModification;
        this.durationTillExpiry = giftCertificateBuilder.durationTillExpiry;
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
        GiftCertificate that = (GiftCertificate) o;
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
        return "GiftCertificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfModification=" + dateOfModification +
                ", durationTillExpiry=" + durationTillExpiry +
                '}';
    }

    public static class GiftCertificateBuilder {

        private final Long id;
        private final String name;
        private final String description;
        private final BigDecimal price;
        private final Integer durationTillExpiry;
        private LocalDate dateOfCreation;
        private LocalDate dateOfModification;

        public GiftCertificateBuilder(long id, String name, String description, BigDecimal price, Integer durationTillExpiry) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.durationTillExpiry = durationTillExpiry;
        }

        public GiftCertificateBuilder setDateOfCreation(LocalDate dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
            return this;
        }

        public GiftCertificateBuilder setDateOfModification(LocalDate dateOfModification) {
            this.dateOfModification = dateOfModification;
            return this;
        }

        public GiftCertificate getResult() {
            return new GiftCertificate(this);
        }
    }

}