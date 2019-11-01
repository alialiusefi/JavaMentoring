package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO getByID(long id) {
        return userConverter.toDTO(userRepository.queryEntity(new FindUserByUserID(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id was not found!")));
    }

    @Override
    public List<UserDTO> getAll(int pageNumber, int size) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public UserDTO add(UserDTO dto) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public boolean delete(UserDTO dto) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public UserDTO update(UserDTO dto) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }

    @Override
    public UserDTO patch(Map<Object, Object> fields, long id) {
        throw new UnsupportedOperationException("Unimplemented Method");
    }
}
