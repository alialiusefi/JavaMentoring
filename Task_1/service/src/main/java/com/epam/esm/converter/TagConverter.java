package com.epam.esm.converter;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagConverter extends BaseConverter implements Converter<TagDTO, Tag> {

    @Autowired
    protected TagConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Tag toEntity(TagDTO dto) {
        return modelMapper.map(dto, Tag.class);
    }

    @Override
    public TagDTO toDTO(Tag entity) {
        return modelMapper.map(entity, TagDTO.class);
    }

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
