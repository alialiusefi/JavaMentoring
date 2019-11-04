package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllUsers;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO getByID(Long id) {
        return userConverter.toDTO(userRepository.queryEntity(new FindUserByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id was not found!")));
    }

    @Override
    public List<UserDTO> getAll(int pageNumber, int size) {
        return userConverter.toDTOList(userRepository.queryList(new FindAllUsers(), pageNumber, size));
    }

    @Override
    public UserDTO add(UserDTO dto) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }


    @Override
    public UserDTO signUp(UserDTO userDTO) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public UserDTO logIn(UserDTO userDTO) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Transactional

    @Override
    public boolean delete(UserDTO dto) {
        UserDTO userDTO = getByID(dto.getId());
        if (userDTO != null) {
            userRepository.delete(userConverter.toEntity(userDTO));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        UserDTO userDTO = getByID(id);
        if (userDTO != null) {
            userRepository.delete(userConverter.toEntity(userDTO));
            return true;
        }
        throw new ResourceNotFoundException("Tag with this id doesn't exist");
    }

    @Transactional
    @Override
    public UserDTO update(UserDTO dto) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Transactional
    @Override
    public UserDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public UserDTO getByUserName(String username) {
        return userConverter.toDTO(userRepository.queryEntity(new FindUserByUserName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with this username" +
                        " was not found!")));
    }


}
