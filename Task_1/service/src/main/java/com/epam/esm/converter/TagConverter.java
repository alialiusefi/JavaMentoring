package com.epam.esm.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter extends BaseConverter implements IConvert<TagDTO, Tag> {
    @Override
    public Tag toEntity(TagDTO dto) {
        return modelMapper.map(dto, Tag.class);
    }

    @Override
    public TagDTO toDTO(Tag entity) {
        return modelMapper.map(entity, TagDTO.class);
    }

    /*  @Override
        public List<GiftCertificate> toEntityList(List<GiftCertificateDTO> dto) {
            List<GiftCertificate> giftCertificates = new ArrayList<>();
            for(GiftCertificateDTO i : dto) {
                giftCertificates.add(toEntity(i));
            }
            return giftCertificates;
        }

        @Override
        public List<GiftCertificateDTO> toDTOList(List<GiftCertificate> entities) {
            List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
            for(GiftCertificate i : entities) {
                giftCertificatesDTO.add(toDTO(i));
            }
            return giftCertificatesDTO;
        }*/
    @Override
    public List<Tag> toEntityList(List<TagDTO> dto) {
        List<Tag> tags = new ArrayList<>();
        for (TagDTO i : dto) {
            tags.add(toEntity(i));
        }
        return tags;
    }

    @Override
    public List<TagDTO> toDTOList(List<Tag> entities) {
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag i : entities) {
            tagDTOs.add(toDTO(i));
        }
        return tagDTOs;
    }
}
