package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "imagemeta")
@Table
public class ImageMetadataEntity extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "imagemetaSequence", sequenceName = "imagemeta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagemetaSequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "key")
    private String key;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    public ImageMetadataEntity() {
    }

    public ImageMetadataEntity(String name, String key, LocalDate dateCreated) {
        this.name = name;
        this.key = key;
        this.dateCreated = dateCreated;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMetadataEntity that = (ImageMetadataEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(key, that.key) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, key, dateCreated);
    }

    @Override
    public String toString() {
        return "ImageMetadataEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
