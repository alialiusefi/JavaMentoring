package com.epam.esm.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

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
}
