package com.epam.esm.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;
import java.util.Objects;

@Validated
public class TagDTO extends DTO {

    /*
        @NotNull(message = "${tag.tagname.notnull}")
    */
    public TagDTO() {
        super();
    }

    @Size(min = 1, max = 16)
    /*@Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect Tag Name Format")*/
/*
    @NotBlank(message = "${tag.tagname.blank}")
*/
    private String name;

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
