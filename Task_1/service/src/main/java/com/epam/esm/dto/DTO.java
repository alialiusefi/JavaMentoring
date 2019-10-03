package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TagDTO.class, name = "Tag"),
        @JsonSubTypes.Type(value = GiftCertificateDTO.class, name = "GiftCertificate")
})
public abstract class DTO {

    protected long id;

    public DTO() {
    }

    public DTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTO dto = (DTO) o;
        return id == dto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DTO{" +
                "id=" + id +
                '}';
    }
}
