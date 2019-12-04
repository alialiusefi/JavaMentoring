package com.epam.esm.service.implementation;

import com.epam.esm.entity.LocalCustomOAuthUser;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
public class CustomUserServiceImpl implements CustomUserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.queryEntity(new FindUserByUserName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with this username" +
                        " was not found!"));
        return new LocalCustomOAuthUser(userEntity.getAuthorityList(),
                new HashMap<String, Object>() {{
                    put("username", userEntity.getUsername());
                }}, "username", userEntity);
    }


}
