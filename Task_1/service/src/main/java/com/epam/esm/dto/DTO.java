package com.epam.esm.dto;

import java.util.Objects;

public abstract class DTO {

    protected long id;

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
