package com.epam.esm.service.implementation;

import com.epam.esm.entity.CustomUser;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        User user = userRepository.queryEntity(new FindUserByUserName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with this username" +
                        " was not found!"));
        return new CustomUser(user);
    }


}
