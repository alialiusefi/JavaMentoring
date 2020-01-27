package com.epam.esm.converter;

import com.epam.esm.dto.ImageMetaDataDTO;
import com.epam.esm.entity.ImageMetadataEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ImageMetaDataConverter extends BaseConverter implements Converter<ImageMetaDataDTO, ImageMetadataEntity> {

    @Autowired
    protected ImageMetaDataConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public ImageMetadataEntity toEntity(ImageMetaDataDTO giftCertificateDTO) {
        return modelMapper.map(giftCertificateDTO, ImageMetadataEntity.class);

    }

    @Override
    public ImageMetaDataDTO toDTO(ImageMetadataEntity entity) {
        return modelMapper.map(entity, ImageMetaDataDTO.class);
    }


    @Override
    public List<ImageMetadataEntity> toEntityList(List<ImageMetaDataDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageMetaDataDTO> toDTOList(List<ImageMetadataEntity> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

}
