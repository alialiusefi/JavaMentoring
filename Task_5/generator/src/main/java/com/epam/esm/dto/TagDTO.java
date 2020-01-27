package com.epam.esm.dto;

import java.util.Objects;


public class TagDTO extends DTO {

    private String name;


    public TagDTO() {
        super();
    }

    public TagDTO(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagDTO tagDTO = (TagDTO) o;
        return Objects.equals(name, tagDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
