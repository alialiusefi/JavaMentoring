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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "giftcertificates")
@Table
public final class GiftCertificate extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "giftcertificatesSequence", sequenceName = "giftcertificates_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "giftcertificatesSequence")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateOfCreation;
    @Column(name = "date_modified")

    private LocalDate dateOfModification;
    @Column(name = "duration_till_expiry", nullable = false)
    private Integer durationTillExpiry;

    @Column(name = "isForSale", nullable = false)
    private boolean isForSale;

    @ManyToMany
    @JoinTable(name = "tagged_giftcertificates", inverseJoinColumns =
            {@JoinColumn(name = "tag_id")},
            joinColumns = {@JoinColumn(name = "gift_certificate_id")})
    private List<Tag> tags;

    public GiftCertificate() {
        super();
    }

    private GiftCertificate(GiftCertificateBuilder giftCertificateBuilder) {
        this.id = giftCertificateBuilder.id;
        this.name = giftCertificateBuilder.name;
        this.description = giftCertificateBuilder.description;
        this.price = giftCertificateBuilder.price;
        this.dateOfCreation = giftCertificateBuilder.dateOfCreation;
        this.dateOfModification = giftCertificateBuilder.dateOfModification;
        this.durationTillExpiry = giftCertificateBuilder.durationTillExpiry;
        this.isForSale = giftCertificateBuilder.isForSale;
        this.tags = giftCertificateBuilder.tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isForSale() {
        return isForSale;
    }

    public void setForSale(boolean forSale) {
        isForSale = forSale;
    }

    public int getDurationTillExpiry() {
        return durationTillExpiry;
    }

    public void setDurationTillExpiry(int durationTillExpiry) {
        this.durationTillExpiry = durationTillExpiry;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return isForSale == that.isForSale &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(dateOfCreation, that.dateOfCreation) &&
                Objects.equals(dateOfModification, that.dateOfModification) &&
                Objects.equals(durationTillExpiry, that.durationTillExpiry) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, dateOfCreation, dateOfModification, durationTillExpiry, isForSale, tags);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfModification=" + dateOfModification +
                ", durationTillExpiry=" + durationTillExpiry +
                ", isForSale=" + isForSale +
                ", tags=" + tags +
                '}';
    }

    public static class GiftCertificateBuilder {

        private final Long id;
        private final String name;
        private final String description;
        private final BigDecimal price;
        private final Integer durationTillExpiry;
        private boolean isForSale;
        private List<Tag> tags;
        private LocalDate dateOfCreation;
        private LocalDate dateOfModification;

        public GiftCertificateBuilder(long id, String name, String description, BigDecimal price, boolean isForSale,
                                      Integer durationTillExpiry) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.isForSale = isForSale;
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

        public GiftCertificateBuilder setTags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public boolean isForSale() {
            return isForSale;
        }

        public void setForSale(boolean forSale) {
            isForSale = forSale;
        }

        public GiftCertificate getResult() {
            return new GiftCertificate(this);
        }
    }

}
