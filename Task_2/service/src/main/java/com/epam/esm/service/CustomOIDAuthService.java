package com.epam.esm.service;

import com.epam.esm.entity.UserEntity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public interface CustomOIDAuthService extends OAuth2UserService<OidcUserRequest, OidcUser> {

    UserEntity registerNewUser(String username);

    OidcUser processOAuth2User(OidcUserRequest userRequest, OidcUser oauthUser);

}
