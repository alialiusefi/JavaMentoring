package com.epam.esm.service.implementation;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Authority;
import com.epam.esm.entity.CustomUser;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.repository.specification.FindUserByUserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.queryEntity(new FindUserByUserName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with this username" +
                        " was not found!"));
        CustomUser customUser = new CustomUser(user);
        return customUser;

    }

    public List<Authority> getAuthorityList(UserDTO userDetails) {
        User user = userRepository.queryEntity(new FindUserByUserID(userDetails.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("User with this id" +
                        "was not found"));
        List<Authority> authorities = user.getAuthorityList();
        return authorities;
    }
}
