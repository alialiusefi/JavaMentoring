package com.epam.esm.service.implementation;

import com.epam.esm.entity.*;
import com.epam.esm.repository.AuthorityRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindUserByUserName;
import com.epam.esm.service.CustomOAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CustomOAuthUserServiceImpl extends DefaultOAuth2UserService
        implements CustomOAuthUserService {

    public UserRepository userRepository;
    public AuthorityRepository authorityRepository;

    private static CustomOAuthUser getCustomOAuthUser(String registrationId,
                                                      Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthenticationProvider.
                GITHUB.toString().toLowerCase())) {
            return new GithubCustomOAuthUser(attributes,
                    Arrays.asList(new Authority.AuthorityBuilder(null, UserStatus.USER)
                            .getResult()));
        } else if (registrationId.equalsIgnoreCase(AuthenticationProvider.
                GOOGLE.toString().toLowerCase())) {
            return new GoogleCustomOAuthUser(attributes,
                    Arrays.asList(new Authority.AuthorityBuilder(null, UserStatus.USER)
                            .getResult()));
        } else {
            throw new UnsupportedOperationException("Sorry! Login with " +
                    registrationId + " is not supported yet.");
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorityRepository(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserEntity registerNewUser(String username) {
        UserEntity.UserBuilder builder = new UserEntity.UserBuilder(
                username,
                "nopass", true);
        UserEntity userEntity = builder.getResult();
        userEntity = userRepository.add(userEntity);
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority.AuthorityBuilder(userEntity.getId(),
                UserStatus.USER).getResult();
        authority = authorityRepository.add(authority);
        authorities.add(authority);
        userEntity.setAuthorityList(authorities);
        userEntity = userRepository.update(userEntity);
        return userEntity;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        return processOAuth2User(userRequest, user);
    }

    @Override
    public CustomOAuthUser processOAuth2User(OAuth2UserRequest userRequest,
                                             OAuth2User oauthUser)
            throws OAuth2AuthenticationException {
        CustomOAuthUser customOAuthUser = getCustomOAuthUser(userRequest.
                getClientRegistration().getRegistrationId(), oauthUser.getAttributes());
        UserEntity userEntity;
        Optional<UserEntity> optionalUser;
        if (!customOAuthUser.getSubject().isEmpty()) {
            String username = customOAuthUser.getSubject();
            optionalUser = userRepository.
                    queryEntity(new FindUserByUserName(username));
            if (optionalUser.isPresent()) {
                userEntity = optionalUser.get();
            } else {
                userEntity = registerNewUser(username);
            }
            return new LocalCustomOAuthUser(userEntity.getUsername(),
                    userEntity.getPassword(), userEntity.getAuthorityList());
        }
        return null;
    }
}
