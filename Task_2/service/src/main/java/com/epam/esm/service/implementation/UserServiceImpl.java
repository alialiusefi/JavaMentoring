package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Authority;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.entity.UserStatus;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.AuthorityRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllUsers;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           BCryptPasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
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
        Optional<UserEntity> user = userRepository.queryEntity(new FindUserByUserName(dto.getUsername()));
        if (user.isPresent()) {
            throw new BadRequestException("User with this username already exists!");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserEntity userEntity = userConverter.toEntity(dto);
        userEntity.setEnabled(true);
        userEntity.setOrders(new ArrayList<>());
        userEntity = userRepository.add(userEntity);
        Authority authority = new Authority.AuthorityBuilder(userEntity, UserStatus.USER).getResult();
        authority = authorityRepository.add(authority);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        userEntity.setAuthorityList(authorities);
        userEntity = userRepository.update(userEntity);
        return userConverter.toDTO(userEntity);
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

    @Override
    public UserDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Unimplemented method!");
    }


}
