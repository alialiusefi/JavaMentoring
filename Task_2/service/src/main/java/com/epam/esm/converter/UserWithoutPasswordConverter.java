package com.epam.esm.converter;

import com.epam.esm.dto.UserWithoutPassDTO;
import com.epam.esm.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserWithoutPasswordConverter extends BaseConverter implements
        Converter<UserWithoutPassDTO, UserEntity> {

    @Autowired
    protected UserWithoutPasswordConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserEntity toEntity(UserWithoutPassDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);

    }

    @Override
    public UserWithoutPassDTO toDTO(UserEntity entity) {
        return modelMapper.map(entity, UserWithoutPassDTO.class);
    }


    @Override
    public List<UserEntity> toEntityList(List<UserWithoutPassDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWithoutPassDTO> toDTOList(List<UserEntity> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }


}

