package com.epam.esm.service.implementation;

import com.epam.esm.entity.LocalCustomOAuthUser;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

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
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user!"));
        Long user_id = userEntity.getId();
        Object[] authorities = userEntity.getAuthorityList().toArray();
        GrantedAuthority authority = (GrantedAuthority) authorities[0];
        String role = authority.getAuthority();
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user_id);
        claims.put("role", role);
        claims.put("username", userEntity.getUsername());
        return new LocalCustomOAuthUser(userEntity.getAuthorityList(), claims,
                "username", userEntity);
    }


}
