package com.epam.esm.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity(name = "tag")
@Table
public class Tag extends AbstractEntity {

    @Column(name = "tag_name", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tagged_giftcertificates", joinColumns =
            {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "gift_certificate_id")})
    private List<GiftCertificate> certificateList;

    private Tag(TagBuilder tagBuilder) {
        super(tagBuilder.id);
        this.name = tagBuilder.name;
        this.certificateList = tagBuilder.certificates;
    }

    public Tag() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<GiftCertificate> certificateList) {
        this.certificateList = certificateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class TagBuilder {

        private final Long id;
        private final String name;
        private List<GiftCertificate> certificates;

        public TagBuilder(long id, String name) {
            this.id = id;
            this.name = name;

        }

        public TagBuilder setGiftCertificates(List<GiftCertificate> giftCertificates) {
            this.certificates = giftCertificates;
            return this;
        }

        public Tag getResult() {
            return new Tag(this);
        }

    }

}
