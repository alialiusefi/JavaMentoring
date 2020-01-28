package com.epam.esm.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class IncorrectFieldNameGiftCertificateDTO extends DTO {


    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    private String name;

    private String description;

    private BigDecimal priceDTO;


    private LocalDate dateOfCreationINCORRECT;


    private LocalDate dateOfModification;
    private Integer durationTillExpiry;

    private List<TagDTO> tags;

    public IncorrectFieldNameGiftCertificateDTO() {
        super();
    }

    public IncorrectFieldNameGiftCertificateDTO(long id, String name, String description, BigDecimal price, LocalDate dateOfCreationINCORRECT,
                                                LocalDate dateOfModification, Integer durationTillExpiry, List<TagDTO> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.priceDTO = price;
        this.dateOfCreationINCORRECT = dateOfCreationINCORRECT;
        this.dateOfModification = dateOfModification;
        this.durationTillExpiry = durationTillExpiry;
        this.tags = tags;
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

    public BigDecimal getPriceDTO() {
        return priceDTO;
    }

    public void setPriceDTO(BigDecimal priceDTO) {
        this.priceDTO = priceDTO.setScale(SCALE, ROUNDING_MODE);
    }

    public LocalDate getDateOfCreationINCORRECT() {
        return dateOfCreationINCORRECT;
    }

    public void setDateOfCreationINCORRECT(LocalDate dateOfCreationINCORRECT) {
        this.dateOfCreationINCORRECT = dateOfCreationINCORRECT;
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

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDTO that = (GiftCertificateDTO) o;
        return getDurationTillExpiry() == that.getDurationTillExpiry() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPriceDTO(), that.getPrice()) &&
                Objects.equals(getDateOfCreationINCORRECT(), that.getDateOfCreation()) &&
                Objects.equals(getDateOfModification(), that.getDateOfModification()) &&
                Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDescription(),
                getPriceDTO(), getDateOfCreationINCORRECT(), getDateOfModification(), getDurationTillExpiry(), getTags());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GiftCertificateDTO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(priceDTO);
        sb.append(", dateOfCreation=").append(dateOfCreationINCORRECT);
        sb.append(", dateOfModification=").append(dateOfModification);
        sb.append(", durationTillExpiry=").append(durationTillExpiry);
        sb.append(", tagDTOList=").append(tags);
        sb.append('}');
        return sb.toString();
    }

}
