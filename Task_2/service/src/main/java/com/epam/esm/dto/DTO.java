package com.epam.esm.dto;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Objects;

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
