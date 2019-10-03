package com.epam.esm.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GiftCertificateDTO extends DTO {

    private String name;
    private String description;
    private BigDecimal price;


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfCreation;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfModification;
    private int durationTillExpiry;

    private List<TagDTO> tagDTOList;

    public GiftCertificateDTO() {
        super();
    }

    public GiftCertificateDTO(long id, String name, String description, BigDecimal price, LocalDate dateOfCreation,
                              LocalDate dateOfModification, int durationTillExpiry, List<TagDTO> tagDTOList) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.dateOfCreation = dateOfCreation;
        this.dateOfModification = dateOfModification;
        this.durationTillExpiry = durationTillExpiry;
        this.tagDTOList = tagDTOList;
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

    public int getDurationTillExpiry() {
        return durationTillExpiry;
    }

    public void setDurationTillExpiry(int durationTillExpiry) {
        this.durationTillExpiry = durationTillExpiry;
    }

    public List<TagDTO> getTagDTOList() {
        return tagDTOList;
    }

    public void setTagDTOList(List<TagDTO> tagDTOList) {
        this.tagDTOList = tagDTOList;
    }
}
