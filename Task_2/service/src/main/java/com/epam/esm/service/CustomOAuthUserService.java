package com.epam.esm.service;

import com.epam.esm.entity.UserEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuthUserService extends OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    UserEntity registerNewUser(String username);

    OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauthUser);

}