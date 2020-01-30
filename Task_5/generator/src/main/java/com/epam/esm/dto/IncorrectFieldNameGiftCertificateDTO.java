package com.epam.esm.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class IncorrectFieldNameGiftCertificateDTO extends DTO {


    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING_MODE = RoundingMode.DOWN;

    private String name;

    private String description;

    private BigDecimal INCORRECTFIELDNAME;

    private Integer durationTillExpiry;

    private List<TagDTO> tags;

    public IncorrectFieldNameGiftCertificateDTO() {
        super();
    }

    public IncorrectFieldNameGiftCertificateDTO(long id, String name, String description, BigDecimal price, Integer duration,
                                                List<TagDTO> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.durationTillExpiry = duration;
        this.INCORRECTFIELDNAME = price;
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

    public BigDecimal getINCORRECTFIELDNAME() {
        return INCORRECTFIELDNAME;
    }

    public void setINCORRECTFIELDNAME(BigDecimal INCORRECTFIELDNAME) {
        this.INCORRECTFIELDNAME = INCORRECTFIELDNAME.setScale(SCALE, ROUNDING_MODE);
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
        GeneratedGiftCertificateDTO that = (GeneratedGiftCertificateDTO) o;
        return getDurationTillExpiry() == that.getDurationTillExpiry() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getINCORRECTFIELDNAME(), that.getPrice()) &&
                Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getDescription(),
                getINCORRECTFIELDNAME(), getDurationTillExpiry(), getTags());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GiftCertificateDTO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(INCORRECTFIELDNAME);
        sb.append(", durationTillExpiry=").append(durationTillExpiry);
        sb.append(", tagDTOList=").append(tags);
        sb.append('}');
        return sb.toString();
    }

}
