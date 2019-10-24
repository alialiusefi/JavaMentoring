package com.epam.esm.entity;


import java.util.Objects;


public class Tag extends Entity {


    private String name;

    private Tag(TagBuilder tagBuilder) {
        super(tagBuilder.id);
        this.name = tagBuilder.name;
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

        public TagBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Tag getResult() {
            return new Tag(this);
        }

    }

}
