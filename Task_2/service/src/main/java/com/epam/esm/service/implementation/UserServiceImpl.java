package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.converter.UserWithoutPasswordConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.UserWithoutPassDTO;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllUsers;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private UserWithoutPasswordConverter userWithoutPasswordConverter;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           UserWithoutPasswordConverter userWithoutPasswordConverter, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.userWithoutPasswordConverter = userWithoutPasswordConverter;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserWithoutPassDTO getByID(Long id) {
        return userWithoutPasswordConverter.toDTO(userRepository.queryEntity(new FindUserByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id was not found!")));
    }

    @Override
    public List<UserWithoutPassDTO> getAll(int pageNumber, int size) {
        return userWithoutPasswordConverter.toDTOList(userRepository.queryList(new FindAllUsers(), pageNumber, size));
    }

    @Transactional
    @Override
    public UserDTO add(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return userConverter.toDTO(userRepository.add(userConverter.toEntity(dto)));
    }

    @Transactional
    @Override
    public boolean delete(UserDTO dto) {
        UserWithoutPassDTO userDTO = getByID(dto.getId());
        if (userDTO != null) {
            userRepository.delete(userWithoutPasswordConverter.toEntity(userDTO));
            return true;
        }
        throw new ResourceNotFoundException("User with this id doesn't exist");
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        UserWithoutPassDTO userDTO = getByID(id);
        if (userDTO != null) {
            userRepository.delete(userWithoutPasswordConverter.toEntity(userDTO));
            return true;
        }
        throw new ResourceNotFoundException("User with this id doesn't exist");
    }

    @Transactional
    @Override
    public UserDTO update(UserDTO dto) {
        if (getByID(dto.getId()) == null) {
            throw new ResourceNotFoundException("User with ID: "
                    + dto.getId() + " was not found!");
        }
        UserEntity user = userConverter.toEntity(dto);
        return userConverter.toDTO(userRepository.update(user));
    }

    @Transactional
    @Override
    public UserDTO patch(Map<Object, Object> fields, Long id) {
        UserEntity user = userRepository.queryEntity(
                new FindUserByUserID(id)).orElseThrow(() ->
                new ResourceNotFoundException("User with this id doesn't exist!"));
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, user, v);
            field.setAccessible(false);
        });
        return update(userConverter.toDTO(user));
    }


}
