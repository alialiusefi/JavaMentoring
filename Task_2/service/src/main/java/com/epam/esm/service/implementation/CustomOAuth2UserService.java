package com.epam.esm.service.implementation;

import com.epam.esm.entity.*;
import com.epam.esm.exception.OAuth2AuthenticationProcessingException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthenticationProvider.GOOGLE.toString().toLowerCase())) {
            return new OAuthGoogle2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthenticationProvider.GITHUB.toString().toLowerCase())) {
            return new OAuthGithub2UserInfo(attributes);
        } else {
            throw new UnsupportedOperationException("Sorry! Login with " +
                    registrationId + " is not supported yet.");
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(userRequest, user);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauthUser)
            throws OAuth2AuthenticationException {
        OAuth2UserInfo userInfo = getOAuth2UserInfo(userRequest.
                getClientRegistration().getRegistrationId(), oauthUser.getAttributes());
        if (StringUtils.isEmpty(userInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        User user;
        Optional<User> optionalUser = userRepository.
                queryEntity(new FindUserByUserName(userInfo.getEmail()));
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = registerNewUser(userRequest, userInfo);
        }
        return new CustomUser(user, oauthUser.getAttributes());

    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User.UserBuilder(
                oAuth2UserInfo.getEmail(),
                null, true).getResult();
        user = userRepository.add(user);
        user.getAuthorityList().add(new Authority.AuthorityBuilder(
                user.getId(), UserStatus.USER).getResult());
        user = userRepository.update(user);
        return user;
    }

}
