package com.epam.esm.converter;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter extends BaseConverter implements Converter<UserDTO, User> {

    @Autowired
    protected UserConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);

    }

    @Override
    public UserDTO toDTO(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }


    @Override
    public List<User> toEntityList(List<UserDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDTOList(List<User> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
