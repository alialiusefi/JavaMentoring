package com.epam.esm.service.implementation;

import com.epam.esm.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    //private UserService userservice;
    private UserConverter userConverter;

    @Autowired
    public CustomUserServiceImpl(/*UserService userservice,*/ UserConverter userConverter) {
        //this.userservice = userservice;
        this.userConverter = userConverter;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* UserDTO userDTO = userservice.getByUserName(username);*/
       /* User user = userConverter.toEntity(userDTO);
        return new CustomUser(user);*/
        return null;
    }


}
