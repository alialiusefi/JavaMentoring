package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllUsers;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserDTO getByID(Long id) {
        UserEntity userEntity = userRepository.queryEntity(new FindUserByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id was not found!"));
        return userConverter.toDTO(userEntity);
    }

    @Override
    public List<UserDTO> getAll(int pageNumber, int size) {
        return userConverter.toDTOList(userRepository.queryList(new FindAllUsers(), pageNumber, size));
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
        UserDTO userDTO = getByID(dto.getId());
        if (userDTO != null) {
            userRepository.delete(userConverter.toEntity(userDTO));
            return true;
        }
        throw new ResourceNotFoundException("User with this id doesn't exist");
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        UserDTO userDTO = getByID(id);
        if (userDTO != null) {
            userRepository.delete(userConverter.toEntity(userDTO));
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



}
