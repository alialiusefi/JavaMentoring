package com.epam.esm.converter;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter extends BaseConverter implements Converter<UserDTO, UserEntity> {

    @Autowired
    protected UserConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserEntity toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);

    }

    @Override
    public UserDTO toDTO(UserEntity entity) {
        return modelMapper.map(entity, UserDTO.class);
    }


    @Override
    public List<UserEntity> toEntityList(List<UserDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDTOList(List<UserEntity> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
