package com.epam.esm.dto;


import java.util.List;
import java.util.Objects;

public class TaggedCertificateDTO extends DTO {

    private GiftCertificateDTO giftCertificateDTO;
    private List<TagDTO> tagDTOList;


    public TaggedCertificateDTO(long id, GiftCertificateDTO giftCertificateDTO, List<TagDTO> tagDTOList) {
        super(id);
        this.giftCertificateDTO = giftCertificateDTO;
        this.tagDTOList = tagDTOList;
    }

    public GiftCertificateDTO getGiftCertificateDTO() {
        return giftCertificateDTO;
    }

    public void setGiftCertificateDTO(GiftCertificateDTO giftCertificateDTO) {
        this.giftCertificateDTO = giftCertificateDTO;
    }

    public List<TagDTO> getTagDTOList() {
        return tagDTOList;
    }

    public void setTagDTOList(List<TagDTO> tagDTOList) {
        this.tagDTOList = tagDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaggedCertificateDTO that = (TaggedCertificateDTO) o;
        return Objects.equals(giftCertificateDTO, that.giftCertificateDTO) &&
                Objects.equals(tagDTOList, that.tagDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificateDTO, tagDTOList);
    }

    @Override
    public String toString() {
        return "TaggedCertificateDTO{" +
                "giftCertificateDTO=" + giftCertificateDTO +
                ", tagDTOList=" + tagDTOList +
                ", id=" + id +
                '}';
    }
}
