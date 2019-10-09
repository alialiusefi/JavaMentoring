package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TagDTO.class, name = "Tag"),
        @JsonSubTypes.Type(value = GiftCertificateDTO.class, name = "GiftCertificate")
})
@Valid
public abstract class DTO {

    @Positive
    protected Long id;

    public DTO() {
    }

    public DTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
