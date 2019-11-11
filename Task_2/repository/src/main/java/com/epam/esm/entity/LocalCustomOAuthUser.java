package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public class LocalCustomOAuthUser extends CustomOAuthUser {

    public LocalCustomOAuthUser(Collection<? extends GrantedAuthority> authorities,
                                Map<String, Object> attributes,
                                String nameAttributeKey, UserEntity userEntity) {
        super(authorities, attributes, nameAttributeKey, userEntity);
    }


}
